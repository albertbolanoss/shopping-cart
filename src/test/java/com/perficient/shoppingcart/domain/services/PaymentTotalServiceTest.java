package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.enumerators.PaymentMethod;
import com.perficient.shoppingcart.domain.services.PaymentTotalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PaymentTotalServiceTest {
    @Autowired
    private PaymentTotalService paymentTotalService;

    @Test
    void calculateTotalWithFeeForVisa() {
        var subtotal =  new BigDecimal("18542.5645475");
        var expected = new BigDecimal("9203.1967310");

        var total = paymentTotalService.calculateTotalWithFee(PaymentMethod.VISA, subtotal);

        assertEquals(expected, total);
    }

    @Test
    void calculateTotalWithFeeForMasterCard() {
        var subtotal =  new BigDecimal("18542.5645475");
        var expected = new BigDecimal("20084.267129400");

        var total = paymentTotalService.calculateTotalWithFee(PaymentMethod.MASTERCARD, subtotal);

        assertEquals(expected, total);
    }

    @Test
    void calculateTotalWithFeeForCash() {
        var subtotal =  new BigDecimal("18542.5645475");
        var expected = new BigDecimal("22251.07745700");

        var total = paymentTotalService.calculateTotalWithFee(PaymentMethod.CASH, subtotal);

        assertEquals(expected, total);
    }
}
