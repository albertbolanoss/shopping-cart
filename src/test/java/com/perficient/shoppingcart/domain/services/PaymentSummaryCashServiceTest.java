package com.perficient.shoppingcart.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentSummaryCashServiceTest {
    private PaymentSummaryCashService paymentTotalCashService;

    @BeforeEach
    void init() {
        paymentTotalCashService = new PaymentSummaryCashService();
    }

    @Test
    void calculateTotalWithFee() {
        var subtotal =  new BigDecimal("18542.5645475");
        var expected = new BigDecimal("22251.07745700");

        var total = paymentTotalCashService.calculateTotalWithFee(subtotal);

        assertEquals(expected, total);
    }
}
