package com.sulek.order.exception;

public class CustomerNotNullException extends RuntimeException{

    public CustomerNotNullException(String message){
        super(message);
    }
}
