package com.payment_processing_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.payment_processing_service.dto.ApiResponse;
import com.payment_processing_service.dto.PaymentRequestDTO;
import com.payment_processing_service.dto.StatusUpdateRequestDTO;
import com.payment_processing_service.dto.StripeSessionResponseDTO;

import com.payment_processing_service.service.PaymentProcessingService;

@RestController
@RequestMapping("/payments")
public class PaymentProcessingController {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentProcessingController.class);


    @Autowired
    private PaymentProcessingService service;



    @PostMapping("/process")
    public ResponseEntity<ApiResponse<?>>
        processPayment(

            @RequestBody
            PaymentRequestDTO request){

        logger.info("Processing payment request");

        StripeSessionResponseDTO response =
                service.processPayment(request);

        return ResponseEntity.ok(

                new ApiResponse<>(
                        "redirect to stripe",
                        response));
    }



    @PostMapping("/status")
    public ResponseEntity<ApiResponse<?>>
        updateStatus(

            @RequestBody
            StatusUpdateRequestDTO request){

        service.updatePaymentStatus(request);

        return ResponseEntity.ok(

                new ApiResponse<>(
                        "status updated",
                        null));
    }
}