package com.rita.classmaster.dto;

import java.time.LocalDate;

public record ClassResponse(String className, LocalDate date, Integer capacity) {
}
