package com.perficient.shoppingcart.domain.exceptions;

/**
 * Already exist exception
 */
public class AlreadyExistException extends CartAppException{
    /**
     * Constructor
     * @param message the exception message
     */
    public AlreadyExistException(String message) {
        super(message);
    }
}
