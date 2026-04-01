package com.payment_processing_service.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.payment_processing_service.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>>
        handleNotFound(ResourceNotFoundException ex){

        logger.error("Not found {}", ex.getMessage());

        return ResponseEntity
                .status(404)
                .body(new ApiResponse<>(ex.getMessage(),null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>>
        handleGeneral(Exception ex){

        logger.error("Error {}", ex.getMessage());

        return ResponseEntity
                .status(500)
                .body(new ApiResponse<>("Internal Error",null));
    }
}