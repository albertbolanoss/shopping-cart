package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.perficient.shoppingcart.application.api.controller.PaymentApi;
import com.perficient.shoppingcart.application.api.model.PaymentSummary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * All related about Payments
 */
@RestController
@Validated
public class PaymentController implements PaymentApi {
    public ResponseEntity<List<PaymentSummary>> getPaymentSummary(String customerId, String paymentMethodId) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
