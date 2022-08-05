package com.rita.classmaster;

import com.rita.classmaster.dto.BookingRequest;
import com.rita.classmaster.dto.BookingResponse;
import com.rita.classmaster.model.Class;
import com.rita.classmaster.repository.ClassesRepository;
import com.rita.classmaster.service.BookingsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookingTests {

    @Autowired
    BookingsService bookingsService;

    @MockBean
    ClassesRepository classesRepository;

    @Test
    public void addBooking() {
        // GIVEN
        Class expectedClass = new Class("Test class", LocalDate.now(), 20);
        when(classesRepository.getByDate(any())).thenReturn(Optional.of(expectedClass));
        BookingRequest bookingRequest = new BookingRequest("Rita", LocalDate.now());

        // WHEN
        BookingResponse bookingResponse = bookingsService.bookClass(bookingRequest);

        // THEN
        assertEquals(expectedClass, bookingResponse.scheduledClass());
        assertEquals("Rita", bookingResponse.memberName());
        assertEquals(Set.of("Rita"), bookingResponse.scheduledClass().members());
    }

    @Test
    public void addBookingNoClassFound() {
        when(classesRepository.getByDate(any())).thenReturn(Optional.empty());
        BookingRequest bookingRequest = new BookingRequest("Rita", LocalDate.now());

        assertThrows(NoSuchElementException.class, () -> bookingsService.bookClass(bookingRequest));
    }
}
