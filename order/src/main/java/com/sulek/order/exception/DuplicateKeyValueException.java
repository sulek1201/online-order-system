package com.sulek.order.exception;

public class DuplicateKeyValueException extends RuntimeException{

    public DuplicateKeyValueException(String message){
        super(message);
    }
}
