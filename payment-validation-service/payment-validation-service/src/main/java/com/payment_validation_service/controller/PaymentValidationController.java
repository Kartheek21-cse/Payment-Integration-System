package com.payment_validation_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.payment_validation_service.dto.ApiResponse;
import com.payment_validation_service.dto.PaymentRequestDTO;
import com.payment_validation_service.dto.PaymentTransactionDTO;
import com.payment_validation_service.service.PaymentValidationService;

@RestController
@RequestMapping("/validate")
public class PaymentValidationController {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    PaymentValidationController.class);

    @Autowired
    private PaymentValidationService service;

    @PostMapping
    public ResponseEntity<ApiResponse<?>>
        validatePayment(

            @Validated
            @RequestBody
            PaymentRequestDTO request){

        logger.info(
         "Incoming validation request for user {}",
                request.getUserId());

        PaymentTransactionDTO response =
                service.validatePayment(request);

        return ResponseEntity.ok(

                new ApiResponse<>(

                        "Validation successful",

                        response));
    }
}