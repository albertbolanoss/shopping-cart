package com.perficient.shoppingcart.domain.exceptions;

/**
 * Cart app exception
 */
public class CartAppException extends RuntimeException {
    /**
     * Constructor
     * @param message the exception message
     */
    public CartAppException(String message) {
        super(message);
    }
}
