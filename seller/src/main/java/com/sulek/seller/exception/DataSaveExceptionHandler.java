package com.sulek.seller.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class DataSaveExceptionHandler {

    @ExceptionHandler(DataSaveException.class)
    public ResponseEntity<?> customerNotNull(DataSaveException dataSaveException){
        List<String> details = new ArrayList<>();
        details.add(dataSaveException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Data process failed",details);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

}
