package com.payment_processing_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.payment_processing_service.entity.PaymentTransaction;

public interface PaymentTransactionRepository
        extends MongoRepository<PaymentTransaction,String>{

    PaymentTransaction findByStripeSessionId(String sessionId);
}