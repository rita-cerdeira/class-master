package com.rita.classmaster.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record BookingRequest(
        @NotBlank String memberName,
        @NotNull LocalDate classDate) {
}
