package com.assessment.helloworldapi.exception;

import com.assessment.helloworldapi.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<ErrorResponse> handleInvalidName(
            InvalidNameException exception) {

        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Invalid Input"));
    }
}
