package com.rita.classmaster.dto;

import com.rita.classmaster.model.Class;

public record BookingResponse(String memberName, Class scheduledClass) {
}
