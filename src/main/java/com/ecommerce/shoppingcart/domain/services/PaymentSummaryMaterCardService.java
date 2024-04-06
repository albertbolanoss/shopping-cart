package com.ecommerce.shoppingcart.domain.services;


import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * To calculate the payment total service with visa
 */
@Service("MASTERCARD")
public class PaymentSummaryMaterCardService implements PaymentSummaryService {
    private final BigDecimal FACTOR = new BigDecimal("0.04");

    private final BigDecimal ADDITIONAL = new BigDecimal(800);
    /**
     * Calculate the total with fee using MASTERCARD
     * @param subtotal the subtotal without fee
     * @return the total with type big decimal
     */
    @Override
    public BigDecimal calculateTotalWithFee(BigDecimal subtotal) {
        return subtotal.add(subtotal.multiply(FACTOR)).add(ADDITIONAL);
    }
}
