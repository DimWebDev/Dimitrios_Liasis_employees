package com.dimitriosliasis.employees.backend.controller;

import com.dimitriosliasis.employees.backend.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.io.UncheckedIOException;
import java.time.Instant;
import java.time.format.DateTimeParseException;

/**
 * Converts common exceptions into a uniform ErrorDto JSON payload.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDto> handleStatus(ResponseStatusException ex) {
        ErrorDto dto = new ErrorDto(
                Instant.now(),
                ex.getStatusCode().value(),
                ex.getReason(),
                ex.getMessage()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(dto);
    }

    @ExceptionHandler({ DateTimeParseException.class, NumberFormatException.class, UncheckedIOException.class })
    public ResponseEntity<ErrorDto> handleBadInput(Exception ex) {
        ErrorDto dto = new ErrorDto(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(dto);
    }
}
