package com.ecommerce.shoppingcart.domain.exceptions;

/**
 * The cart is empty or null
 */
public class CartEmptyException extends RuntimeException {
    /**
     * Constructor
     * @param message the exception message
     */
    public CartEmptyException(String message) {
        super(message);
    }
}
