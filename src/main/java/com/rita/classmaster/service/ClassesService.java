package com.rita.classmaster.service;

import com.rita.classmaster.dto.ClassRequest;
import com.rita.classmaster.dto.ClassResponse;
import com.rita.classmaster.mapper.ClassMapper;
import com.rita.classmaster.model.Class;
import com.rita.classmaster.repository.ClassesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
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

        if (classRequest.endDate().isBefore(classRequest.startDate())) {
            throw new IllegalArgumentException("End date can't be after start date!");
        }

        List<Class> classesToCreate = classRequest.startDate().datesUntil(classRequest.endDate().plusDays(1))
                .filter(date -> !classesRepository.existsInDay(date))
                .map(date -> new Class(classRequest.className(), date, classRequest.capacity()))
                .collect(Collectors.toList());

        logger.debug("{} classes being added: {}", classesToCreate.size(), classesToCreate);
        classesRepository.add(classesToCreate);

        return classesToCreate.stream().map(classMapper::classToClassResponse).collect(Collectors.toList());
    }
}
