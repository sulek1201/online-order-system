package com.sulek.seller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<?> customerNotFound(SellerNotFoundException sellerNotFoundException){
        List<String> details = new ArrayList<>();
        details.add(sellerNotFoundException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Seller is not found",details);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
