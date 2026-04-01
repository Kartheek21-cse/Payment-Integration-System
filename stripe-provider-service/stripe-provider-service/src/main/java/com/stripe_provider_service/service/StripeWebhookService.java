package com.stripe_provider_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import com.stripe_provider_service.dto.StatusUpdateRequestDTO;

@Service
public class StripeWebhookService {

    private static final Logger logger=
            LoggerFactory.getLogger(
                    StripeWebhookService.class);

    @Autowired
    private RestTemplate restTemplate;


    private final String PROCESSING_URL=
            "http://localhost:8082/payments/status";


    public void notifyPaymentSuccess(
            String sessionId){

        logger.info(
                "Notifying processing service");

        StatusUpdateRequestDTO request=
                new StatusUpdateRequestDTO(
                        sessionId,
                        "SUCCESS");


        restTemplate.postForObject(

                PROCESSING_URL,

                request,

                String.class);
    }
}