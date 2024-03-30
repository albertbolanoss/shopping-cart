package com.perficient.shoppingcart.domain.exceptions;

/**
 * This exception is throw when a product is not available en the stock
 */
public class ProductNotAvailableException extends RuntimeException {
    /**
     * Constructor
     * @param message the exception message
     */
    public ProductNotAvailableException(String message) {
        super(message);
    }
}
