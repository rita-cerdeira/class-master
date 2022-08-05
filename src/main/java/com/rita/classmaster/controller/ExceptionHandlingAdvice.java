package com.rita.classmaster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ExceptionHandlingAdvice {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandlingAdvice.class);

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException exception, WebRequest request) {
        logger.debug("Illegal argument: {}. Request: {}", exception.getMessage(), request);
        return new ResponseEntity<>(Map.of("error", exception.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logger.debug("Invalid arguments: {}", errors);
        return new ResponseEntity<>(errors, BAD_REQUEST);
    }
}
