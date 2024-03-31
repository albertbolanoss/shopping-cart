package com.perficient.shoppingcart.domain.exceptions;

/**
 * This exception is throw when an element not exist
 */
public class NotExistException extends RuntimeException{
    /**
     * Constructor
     * @param message the exception message
     */
    public NotExistException(String message) {
        super(message);
    }
}
