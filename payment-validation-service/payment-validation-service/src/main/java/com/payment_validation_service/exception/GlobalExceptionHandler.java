package com.payment_validation_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.payment_validation_service.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<?>>
        handleValidationException(ValidationException ex){

        logger.error("Validation error {}", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>>
        handleMethodArgumentException(
                MethodArgumentNotValidException ex){

        String msg =
                ex.getBindingResult()
                  .getFieldError()
                  .getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(new ApiResponse<>(msg, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>>
        handleGeneral(Exception ex){

        logger.error("Internal error {}", ex.getMessage());

        return ResponseEntity
                .internalServerError()
                .body(new ApiResponse<>(
                        "Internal Server Error",
                        null));
    }
}