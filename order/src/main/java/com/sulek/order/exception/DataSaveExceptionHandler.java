package com.sulek.order.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class DataSaveExceptionHandler {

    @ExceptionHandler(DuplicateKeyValueException.class)
    public ResponseEntity<?> customerNotNull(DuplicateKeyValueException duplicateKeyValueException){
        List<String> details = new ArrayList<>();
        details.add(duplicateKeyValueException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Already defined user",details);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
