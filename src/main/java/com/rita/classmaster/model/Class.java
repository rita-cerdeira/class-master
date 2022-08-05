package com.rita.classmaster.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public record Class(String className, LocalDate date, Integer capacity, Set<String> members) {
    public Class(String className, LocalDate date, Integer capacity) {
        this(className, date, capacity, new HashSet<>());
    }
}
