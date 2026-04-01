package com.payment_validation_service.exception;

public class ValidationException
        extends RuntimeException {

    public ValidationException(String message){
        super(message);
    }
}