package com.perficient.shoppingcart.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTotalVisaServiceTest {
    private PaymentTotalVisaService paymentTotalVisaService;

    @BeforeEach
    void init() {
        paymentTotalVisaService = new PaymentTotalVisaService();
    }

    @Test
    void calculateTotalWithFee() {
        var subtotal =  new BigDecimal("18542.5645475");
        var expected = new BigDecimal("9203.1967310");

        var total = paymentTotalVisaService.calculateTotalWithFee(subtotal);

        assertEquals(expected, total);
    }
}
