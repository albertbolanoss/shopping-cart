package com.ecommerce.customer.infrastructure.mother;

import com.ecommerce.shared.infrastructure.mother.FakerMother;
import com.perficient.shoppingcart.application.api.model.AddUserReq;

/**
 * Generate data for Add user request instances
 */
public class AddCustomerReqMother {

    /**
     * Generate a Add User Request with random data
     * @return a instance of AddUserReq
     */
    public static AddUserReq random() {
        return new AddUserReq()
                .firstName(FakerMother.getFaker().name().firstName())
                .lastName(FakerMother.getFaker().name().lastName())
                .email(FakerMother.getFaker().internet().emailAddress())
                .phone(FakerMother.getFaker().phoneNumber().phoneNumberInternational())
                .password(FakerMother.getFaker().internet().password());
    }

    /**
     * Generate a Add User Request with random data
     * @return a instance of AddUserReq
     */
    public static AddUserReq nullable() {
        return new AddUserReq()
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
    public static AddUserReq invalidMaxLength() {
        return new AddUserReq()
                .firstName(FakerMother.getFaker().lorem().characters(CustomerDomainMother.FIRSTNAME_MAX_LENGTH + 1))
                .lastName(FakerMother.getFaker().lorem().characters((CustomerDomainMother.LASTNAME_MAX_LENGTH + 1)))
                .email(FakerMother.getFaker().lorem().characters((CustomerDomainMother.EMAIL_MAX_LENGTH + 1)))
                .phone(FakerMother.getFaker().lorem().characters((CustomerDomainMother.PHONE_MAX_LENGTH + 1)))
                .password(FakerMother.getFaker().lorem().characters((CustomerDomainMother.PASSWORD_MAX_LENGTH + 1)));
    }

    /**
     * Generate an Add User Request with random data
     * @return an instance of AddUserReq
     */
    public static AddUserReq invalidEmail() {
        return new AddUserReq()
                .firstName(FakerMother.getFaker().name().firstName())
                .lastName(FakerMother.getFaker().name().lastName())
                .email(FakerMother.getFaker().internet().username())
                .phone(FakerMother.getFaker().phoneNumber().phoneNumberInternational())
                .password(FakerMother.getFaker().internet().password());
    }
}
