package com.payment_processing_service.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment_processing_service.dto.ApiResponse;
import com.payment_processing_service.dto.PaymentRequestDTO;
import com.payment_processing_service.dto.StatusUpdateRequestDTO;
import com.payment_processing_service.dto.StripeSessionResponseDTO;
import com.payment_processing_service.entity.PaymentTransaction;
import com.payment_processing_service.exceptions.ResourceNotFoundException;
import com.payment_processing_service.repository.PaymentTransactionRepository;

@Service
public class PaymentProcessingService {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentProcessingService.class);

    @Autowired
    private PaymentTransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;


    private final String STRIPE_URL =
            "http://localhost:8083/stripe/create-session";


    public StripeSessionResponseDTO
        processPayment(PaymentRequestDTO request){

        logger.info("Creating payment record");

        PaymentTransaction tx =
                new PaymentTransaction();

        tx.setUserId(request.getUserId());
        tx.setAmount(request.getAmount());
        tx.setCurrency(request.getCurrency());
        tx.setMerchantId(request.getMerchantId());

        tx.setStatus("PENDING");
        tx.setCreatedAt(LocalDateTime.now());

        repository.save(tx);
        logger.info("Calling stripe service");

        ResponseEntity<ApiResponse<StripeSessionResponseDTO>> responseEntity =
                restTemplate.exchange(
                        STRIPE_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(request),
                        new ParameterizedTypeReference<ApiResponse<StripeSessionResponseDTO>>() {}
                );
	
        ApiResponse<StripeSessionResponseDTO> apiResponse =
                responseEntity.getBody();

        StripeSessionResponseDTO stripeResponse =
                apiResponse.getData();	

        tx.setStripeSessionId(stripeResponse.getSessionId());

        repository.save(tx);

        logger.info("Stripe session stored {}", stripeResponse.getSessionId());

        return stripeResponse;
    }



    public void updatePaymentStatus(
            StatusUpdateRequestDTO request){

        PaymentTransaction tx =
                repository.findByStripeSessionId(
                        request.getSessionId());

        if(tx == null){

            throw new ResourceNotFoundException(
                    "Payment not found");
        }

        tx.setStatus(request.getStatus());

        repository.save(tx);

        logger.info("Payment updated {}", request.getStatus());
    }
}