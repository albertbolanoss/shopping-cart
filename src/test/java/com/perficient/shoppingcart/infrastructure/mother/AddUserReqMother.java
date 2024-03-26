package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.application.api.model.AddCustomerReq;

/**
 * Generate data for Add user request instances
 */
public class AddUserReqMother {

    /**
     * Generate a Add User Request with random data
     * @return a instance of AddUserReq
     */
    public static AddCustomerReq random() {
        return new AddCustomerReq()
                .firstName(FakerMother.randomFirstname())
                .lastName(FakerMother.randomLastname())
                .email(FakerMother.randomEmail())
                .phone(FakerMother.randomInternationalPhoneNumber())
                .password(FakerMother.randomPassword());
    }
}
