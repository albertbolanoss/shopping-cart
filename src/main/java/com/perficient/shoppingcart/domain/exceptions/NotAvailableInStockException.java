package com.perficient.shoppingcart.domain.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Throws when there is no availability in the stock of the products to sell
 */
public class NotAvailableInStockException extends RuntimeException {
    List<ObjectError> errors;
    public NotAvailableInStockException(String message, List<ObjectError> errors) {
        super(message);
        this.errors = errors;
    }
}
