package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.application.api.model.AddUserReq;

/**
 * Generate data for Add user request instances
 */
public class AddUserReqMother {
    /**
     * First name max length
     */
    private static final int FIRSTNAME_MAX_LENGTH = 125;
    /**
     * Last name max length
     */
    private static final int LASTNAME_MAX_LENGTH = 125;

    public static AddUserReq random() {
        return new AddUserReq()
                .firstName(FakerMother.randomFirstname(FIRSTNAME_MAX_LENGTH))
                .lastName(FakerMother.randomLastname(LASTNAME_MAX_LENGTH))
                .email(FakerMother.randomEmail())
                .phone(FakerMother.randomInternationalPhoneNumber())
                .password(FakerMother.randomPassword());
    }
}
