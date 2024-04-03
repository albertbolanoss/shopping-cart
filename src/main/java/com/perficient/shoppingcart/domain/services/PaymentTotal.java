package com.perficient.shoppingcart.domain.services;

import java.math.BigDecimal;

/**
 * To calculate the payment total service
 */
public interface PaymentTotal {

    /**
     * Calculate the total to pay with fee
     * @param subtotal the subtotal without fee
     * @return a total to pay of type big decimal
     */
    BigDecimal calculateTotalWithFee(BigDecimal subtotal);
}