package com.perficient.shoppingcart.domain.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTotalMasterCardServiceTest {
    private PaymentTotalMaterCardService paymentTotalMaterCardService;

    @BeforeEach
    void init() {
        paymentTotalMaterCardService = new PaymentTotalMaterCardService();
    }

    @Test
    void calculateTotalWithFee() {
        var subtotal =  new BigDecimal("18542.5645475");
        var expected = new BigDecimal("20084.267129400");

        var total = paymentTotalMaterCardService.calculateTotalWithFee(subtotal);

        assertEquals(expected, total);
    }
}
