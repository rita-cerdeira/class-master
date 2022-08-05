package com.rita.classmaster.repository;

import com.rita.classmaster.model.Class;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClassesRepository {
    private List<Class> scheduledClasses = new ArrayList<>();

    public void add(List<Class> classesToAdd) {
        scheduledClasses.addAll(classesToAdd);
    }

    public Optional<Class> getByDate(LocalDate localDate) {
        return scheduledClasses.stream().filter(scheduledClass -> localDate.isEqual(scheduledClass.date())).findFirst();
    }
    public boolean existsInDay(LocalDate date) {
        return scheduledClasses.stream().anyMatch(scheduledClass -> scheduledClass.date().isEqual(date));
    }
}
