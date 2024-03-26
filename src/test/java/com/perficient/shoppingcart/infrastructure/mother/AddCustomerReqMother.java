package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.application.api.model.AddCustomerReq;

/**
 * Generate data for Add user request instances
 */
public class AddCustomerReqMother {

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

    /**
     * Generate a Add User Request with random data
     * @return a instance of AddUserReq
     */
    public static AddCustomerReq nullable() {
        return new AddCustomerReq()
                .firstName(null)
                .lastName(null)
                .email(null)
                .phone(null)
                .password(null);
    }

    /**
     * Generate a Add User Request with random data
     * @return a instance of AddUserReq
     */
    public static AddCustomerReq invalidMaxLength() {
        return new AddCustomerReq()
                .firstName(FakerMother.randomText(CustomerMother.FIRSTNAME_MAX_LENGTH + 1))
                .lastName(FakerMother.randomText(CustomerMother.LASTNAME_MAX_LENGTH + 1))
                .email(FakerMother.randomText(CustomerMother.EMAIL_MAX_LENGTH + 1))
                .phone(FakerMother.randomText(CustomerMother.PHONE_MAX_LENGTH + 1))
                .password(FakerMother.randomText(CustomerMother.PASSWORD_MAX_LENGTH + 1));
    }

    /**
     * Generate a Add User Request with random data
     * @return a instance of AddUserReq
     */
    public static AddCustomerReq invalidEmail() {
        return new AddCustomerReq()
                .firstName(FakerMother.randomFirstname())
                .lastName(FakerMother.randomLastname())
                .email(FakerMother.randomUsername())
                .phone(FakerMother.randomInternationalPhoneNumber())
                .password(FakerMother.randomPassword());
    }
}
