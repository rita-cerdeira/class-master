package com.rita.classmaster.service;

import com.rita.classmaster.dto.BookingRequest;
import com.rita.classmaster.dto.BookingResponse;
import com.rita.classmaster.model.Class;
import org.springframework.stereotype.Service;

@Service
public class BookingsService {

    private final ClassesService classesService;

    public BookingsService(ClassesService classesService) {
        this.classesService = classesService;
    }

    public BookingResponse bookClass(BookingRequest bookingRequest) {
        Class bookedClass = classesService.addMemberToClass(bookingRequest.memberName(), bookingRequest.classDate());
        return new BookingResponse(bookingRequest.memberName(), bookedClass);
    }
}
