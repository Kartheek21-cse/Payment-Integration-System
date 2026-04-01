package com.payment_processing_service.exceptions;

public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException(String msg){

        super(msg);
    }
}