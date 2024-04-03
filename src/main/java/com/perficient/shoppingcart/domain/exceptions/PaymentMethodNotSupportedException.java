package com.perficient.shoppingcart.domain.exceptions;

/**
 * Manage the not supported payment method exception
 */
public class PaymentMethodNotSupportedException extends RuntimeException {
    /**
     * Constructor
     * @param message the exception message
     */
    public PaymentMethodNotSupportedException(String message) {
        super(message);
    }
}
