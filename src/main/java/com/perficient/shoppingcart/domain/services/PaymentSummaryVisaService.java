package com.perficient.shoppingcart.domain.services;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * To calculate the payment total service with visa
 */
@Service("VISA")
public class PaymentSummaryVisaService implements PaymentSummaryService {
    /**
     * Calculate the total with fee using VISA
     * @param subtotal the subtotal without fee
     * @return the total with type big decimal
     */
    @Override
    public BigDecimal calculateTotalWithFee(BigDecimal subtotal) {
        return subtotal.subtract(subtotal.sqrt(new MathContext(10)))
                .divide(new BigDecimal(2), RoundingMode.HALF_UP);

    }
}
