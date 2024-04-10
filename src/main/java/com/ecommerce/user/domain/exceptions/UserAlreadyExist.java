package com.ecommerce.user.domain.exceptions;

import com.ecommerce.shared.domain.exceptions.AlreadyExistException;

/**
 * The user already exists exception
 */
public class UserAlreadyExist extends AlreadyExistException {
    /**
     * Constructor
     * @param userEmail the user email that already exist
     */
    public UserAlreadyExist(String userEmail) {
        super(String.format("The user with the email (%s) already exists", userEmail));
    }
}
