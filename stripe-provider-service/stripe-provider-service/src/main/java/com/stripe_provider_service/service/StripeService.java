package com.stripe_provider_service.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import com.stripe_provider_service.dto.StripeSessionRequestDTO;
import com.stripe_provider_service.dto.StripeSessionResponseDTO;

@Service
public class StripeService {

    private static final Logger logger=
            LoggerFactory.getLogger(StripeService.class);


    public StripeSessionResponseDTO
        createCheckoutSession(
                StripeSessionRequestDTO request)
            throws Exception{

        logger.info("Creating stripe session");


        SessionCreateParams params=

                SessionCreateParams.builder()

                .setMode(
                        SessionCreateParams.Mode.PAYMENT)

                .setSuccessUrl(
                        "http://localhost:3000/success")

                .setCancelUrl(
                        "http://localhost:3000/cancel")

                .addLineItem(

                        SessionCreateParams.LineItem.builder()

                        .setQuantity(1L)

                        .setPriceData(

                                SessionCreateParams
                                .LineItem
                                .PriceData
                                .builder()

                                .setCurrency(
                                        request.getCurrency())

                                .setUnitAmount(

                                        request.getAmount()

                                        .multiply(
                                                new BigDecimal(100))

                                        .longValue())

                                .setProductData(

                                        SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder()

                                        .setName(
                                                "Order Payment")

                                        .build())

                                .build())

                        .build())

                .build();



        Session session=
                Session.create(params);


        StripeSessionResponseDTO response=
                new StripeSessionResponseDTO();

        response.setSessionId(
                session.getId());

        response.setCheckoutUrl(
                session.getUrl());


        logger.info(
                "Stripe session created {}",
                session.getId());

        return response;
    }
}