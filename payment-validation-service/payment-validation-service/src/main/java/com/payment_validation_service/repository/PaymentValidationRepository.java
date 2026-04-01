package com.payment_validation_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.payment_validation_service.entity.PaymentValidationLog;

public interface PaymentValidationRepository
        extends MongoRepository<PaymentValidationLog, String> {
}