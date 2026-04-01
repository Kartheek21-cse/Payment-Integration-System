package com.stripe_provider_service.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import com.stripe_provider_service.service.StripeWebhookService;

@RestController
@RequestMapping("/stripe")
public class StripeWebhookController {

    private static final Logger logger=
            LoggerFactory.getLogger(
                    StripeWebhookController.class);


    @Value("${stripe.webhook.secret}")
    private String webhookSecret;


    private final StripeWebhookService webhookService;


    public StripeWebhookController(
            StripeWebhookService webhookService){

        this.webhookService=webhookService;
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(

            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature)
            throws Exception {

        Event event = Webhook.constructEvent(
                payload,
                signature,
                webhookSecret);

        logger.info("Stripe event received {}", event.getType());

        if ("checkout.session.completed".equals(event.getType())) {

            EventDataObjectDeserializer dataDeserializer =
                    event.getDataObjectDeserializer();

            Optional<StripeObject> stripeObject =
                    dataDeserializer.getObject();

            Session session = null;

            if (stripeObject.isPresent()) {

                session = (Session) stripeObject.get();

            } else {

                // fallback parsing
                StripeObject rawObject =
                        dataDeserializer.deserializeUnsafe();

                session = (Session) rawObject;
            }

            logger.info(
                    "Payment success session {}",
                    session.getId());

            webhookService.notifyPaymentSuccess(
                    session.getId());
        }

        return ResponseEntity.ok("received");
    }}