package com.stripe_provider_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.stripe.model.Event;
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
    public ResponseEntity<String>
        handleWebhook(

            @RequestBody String payload,

            @RequestHeader(
                    "Stripe-Signature")
            String signature)
            throws Exception{


        Event event=
                Webhook.constructEvent(

                        payload,

                        signature,

                        webhookSecret);



        if(event.getType()
                .equals(
                  "checkout.session.completed")){


            Session session=

                    (Session)
                    event.getDataObjectDeserializer()

                    .getObject()

                    .get();


            logger.info(
                    "payment success {}",
                    session.getId());


            webhookService.notifyPaymentSuccess(

                    session.getId());
        }


        return ResponseEntity.ok("received");
    }
}