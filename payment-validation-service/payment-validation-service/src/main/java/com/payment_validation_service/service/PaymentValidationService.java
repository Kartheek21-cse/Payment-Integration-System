package com.payment_validation_service.service;

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

import com.payment_validation_service.dto.ApiResponse;
import com.payment_validation_service.dto.PaymentRequestDTO;
import com.payment_validation_service.dto.PaymentTransactionDTO;
import com.payment_validation_service.entity.PaymentValidationLog;
import com.payment_validation_service.exception.ValidationException;
import com.payment_validation_service.repository.PaymentValidationRepository;

@Service
public class PaymentValidationService {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentValidationService.class);

    @Autowired
    private PaymentValidationRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String PROCESSING_URL =
            "http://localhost:8082/processingservice/v1/payments/process";


    public PaymentTransactionDTO validatePayment(
            PaymentRequestDTO request){

        logger.info("Validating payment request");

        if(!request.getCurrency().matches("INR|USD|EUR")){
            throw new ValidationException(
                    "Unsupported currency");
        }

        PaymentValidationLog log =
                new PaymentValidationLog();

        log.setUserId(request.getUserId());
        log.setAmount(request.getAmount());
        log.setCurrency(request.getCurrency());
        log.setMerchantId(request.getMerchantId());

        log.setValidationStatus("SUCCESS");
        log.setMessage("Validation successful");
        log.setCreatedAt(LocalDateTime.now());

        repository.save(log);

        logger.info("Validation success, calling processing service");

        ResponseEntity<ApiResponse<PaymentTransactionDTO>> responseEntity =
                restTemplate.exchange(
                        PROCESSING_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(request),
                        new ParameterizedTypeReference<ApiResponse<PaymentTransactionDTO>>() {}
                );

        ApiResponse<PaymentTransactionDTO> apiResponse =
                responseEntity.getBody();

        PaymentTransactionDTO response =
                apiResponse.getData();
        response.setAmount(request.getAmount());
        response.setCurrency(request.getCurrency());
        response.setStatus("pending");

        return response;
    }
}