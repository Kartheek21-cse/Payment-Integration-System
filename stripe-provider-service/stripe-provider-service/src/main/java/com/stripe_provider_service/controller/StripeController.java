package com.stripe_provider_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.stripe_provider_service.dto.ApiResponse;
import com.stripe_provider_service.dto.StripeSessionRequestDTO;
import com.stripe_provider_service.dto.StripeSessionResponseDTO;

import com.stripe_provider_service.service.StripeService;

@RestController
@RequestMapping("/stripe")
public class StripeController {

    private static final Logger logger=
            LoggerFactory.getLogger(StripeController.class);

    @Autowired
    private StripeService service;


    @PostMapping("/create-session")
    public ResponseEntity<ApiResponse<?>>
        createSession(

                @RequestBody
                StripeSessionRequestDTO request)
            throws Exception{

        logger.info("Incoming stripe request");


        StripeSessionResponseDTO response=

                service.createCheckoutSession(request);


        return ResponseEntity.ok(

                new ApiResponse<>(

                        "session created",

                        response));
    }
}