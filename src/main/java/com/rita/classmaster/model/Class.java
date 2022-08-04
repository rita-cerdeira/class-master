package com.rita.classmaster.model;

import java.time.LocalDate;

public record Class(String className, LocalDate date, Integer capacity) {
}
