package com.rita.classmaster.dto;

import java.time.Instant;
import java.time.LocalDate;

public record ClassRequest(String className, LocalDate startDate, LocalDate endDate, Integer capacity) {
}
