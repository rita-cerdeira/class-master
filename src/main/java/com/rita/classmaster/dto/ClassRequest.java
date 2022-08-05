package com.rita.classmaster.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public record ClassRequest(
        @NotBlank(message = "Class name is mandatory.") String className,
        @NotNull(message = "Class needs to have a start date.") LocalDate startDate,
        LocalDate endDate,
        @Positive(message = "Class capacity needs to be greater than zero.") Integer capacity) {
}
