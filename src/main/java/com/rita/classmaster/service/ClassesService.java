package com.rita.classmaster.service;

import com.rita.classmaster.dto.ClassRequest;
import com.rita.classmaster.dto.ClassResponse;
import com.rita.classmaster.mapper.ClassMapper;
import com.rita.classmaster.model.Class;
import com.rita.classmaster.repository.ClassesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassesService {

    private final static Logger logger = LoggerFactory.getLogger(ClassesService.class);

    private final ClassesRepository classesRepository;
    private final ClassMapper classMapper;

    public ClassesService(ClassesRepository classesRepository, ClassMapper classMapper) {
        this.classesRepository = classesRepository;
        this.classMapper = classMapper;
    }

    public List<ClassResponse> createClasses(ClassRequest classRequest) {

        LocalDate endDate = classRequest.endDate() == null ? classRequest.startDate() : classRequest.endDate();

        if (endDate.isBefore(classRequest.startDate())) {
            throw new IllegalArgumentException("End date can't be after start date!");
        }

        List<Class> classesToCreate = classRequest.startDate().datesUntil(endDate.plusDays(1))
                .filter(date -> !classesRepository.existsInDay(date))
                .map(date -> new Class(classRequest.className(), date, classRequest.capacity()))
                .collect(Collectors.toList());

        logger.debug("{} classes being added: {}", classesToCreate.size(), classesToCreate);
        classesRepository.add(classesToCreate);

        return classesToCreate.stream().map(classMapper::classToClassResponse).collect(Collectors.toList());
    }

    public Class addMemberToClass(String member, LocalDate date) {
        Class bookedClass = findClassInDate(date).orElseThrow(() -> new NoSuchElementException("Class not found for given date."));
        bookedClass.members().add(member);
        return bookedClass;
    }

    private Optional<Class> findClassInDate(LocalDate date) {
        return classesRepository.getByDate(date);
    }

}
