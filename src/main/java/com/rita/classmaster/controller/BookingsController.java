package com.rita.classmaster.controller;

import com.rita.classmaster.dto.BookingRequest;
import com.rita.classmaster.dto.BookingResponse;
import com.rita.classmaster.service.BookingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("bookings")
public class BookingsController {

    private final static Logger logger = LoggerFactory.getLogger(BookingsController.class);

    private final BookingsService bookingsService;

    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping
    public BookingResponse bookClass(@RequestBody @Valid BookingRequest bookingRequest) {
        logger.info("Received request to book class. {}", bookingRequest);
        return bookingsService.bookClass(bookingRequest);
    }
}
