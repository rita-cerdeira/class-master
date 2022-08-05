package com.rita.classmaster;

import com.rita.classmaster.dto.ClassRequest;
import com.rita.classmaster.dto.ClassResponse;
import com.rita.classmaster.model.Class;
import com.rita.classmaster.repository.ClassesRepository;
import com.rita.classmaster.service.ClassesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClassTests {

    @Autowired
    ClassesService classesService;

    @MockBean
    ClassesRepository classesRepository;

    @Test
    public void addClass() {
        // GIVEN
        when(classesRepository.existsInDay(any())).thenReturn(false); // Mocking the "database"
        ClassRequest classRequest = new ClassRequest("Test Class", LocalDate.now().minusDays(9), LocalDate.now(), 20);

        // WHEN
        List<ClassResponse> classResponse = classesService.createClasses(classRequest);

        // THEN
        assertEquals(10, classResponse.size());
    }

    @Test
    public void addClassNoAvailableDate() {
        // GIVEN
        when(classesRepository.existsInDay(any())).thenReturn(true); // Mocking the "database"
        ClassRequest classRequest = new ClassRequest("Test Class", LocalDate.now().minusDays(4), LocalDate.now(), 20);

        // WHEN
        List<ClassResponse> classResponse = classesService.createClasses(classRequest);

        // THEN
        assertEquals(0, classResponse.size());
    }

    @Test
    public void addClassCheckParams() {
        // GIVEN
        when(classesRepository.existsInDay(any())).thenReturn(false); // Mocking the "database"
        ClassRequest classRequest = new ClassRequest("Test Class", LocalDate.now().minusDays(1), LocalDate.now(), 20);

        // WHEN
        List<ClassResponse> classResponses = classesService.createClasses(classRequest);

        // THEN
        List<ClassResponse> expectedClassResponses = List.of(new ClassResponse("Test Class", LocalDate.now().minusDays(1), 20),
                new ClassResponse("Test Class", LocalDate.now(), 20));

        List<Class> expectedClasses = List.of(new Class("Test Class", LocalDate.now().minusDays(1), 20),
                new Class("Test Class", LocalDate.now(), 20));

        assertEquals(expectedClassResponses, classResponses);
        verify(classesRepository, times(1)).add(expectedClasses);
    }

    @Test
    public void addClassDateException() {
        when(classesRepository.existsInDay(any())).thenReturn(false); // Mocking the "database"
        ClassRequest classRequest = new ClassRequest("Test Class", LocalDate.now().plusDays(1), LocalDate.now(), 20);

        assertThrows(IllegalArgumentException.class, () -> classesService.createClasses(classRequest), "Expected IllegalArgumentException.");
    }
}
