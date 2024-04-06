package com.ecommerce.shoppingcart.domain.services;

import com.ecommerce.shoppingcart.domain.services.PaymentSummaryMaterCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentSummaryMasterCardServiceTest {
    private PaymentSummaryMaterCardService paymentTotalMaterCardService;

    @BeforeEach
    void init() {
        paymentTotalMaterCardService = new PaymentSummaryMaterCardService();
    }

    @Test
    void calculateTotalWithFee() {
        var subtotal =  new BigDecimal("18542.5645475");
        var expected = new BigDecimal("20084.267129400");

        var total = paymentTotalMaterCardService.calculateTotalWithFee(subtotal);

        assertEquals(expected, total);
    }
}
