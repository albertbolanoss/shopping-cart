package com.ecommerce.shared.domain.exceptions;

/**
 * Already exist exception
 */
public class AlreadyExistException extends RuntimeException{
    /**
     * Constructor
     * @param message the exception message
     */
    public AlreadyExistException(String message) {
        super(message);
    }
}
