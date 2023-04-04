package com.sulek.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomerNotNullException.class)
    public ResponseEntity<?> customerNotNull(CustomerNotNullException customerNotNullException){
        List<String> details = new ArrayList<>();
        details.add(customerNotNullException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Login or Register request is missing",details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> customerNotFound(CustomerNotFoundException customerNotFoundException){
        List<String> details = new ArrayList<>();
        details.add(customerNotFoundException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("User is not found",details);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
