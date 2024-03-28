package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;

/**
 * Genera random data for Customer Model
 */
public class CustomerDomainMother {
    /*
     * First name max length
     */
    public static final int FIRSTNAME_MAX_LENGTH = 125;
    /**
     * Last name max length
     */
    public static final int LASTNAME_MAX_LENGTH = 125;
    /**
     * Email max length
     */
    public static final int EMAIL_MAX_LENGTH = 255;
    /**
     * Password max length
     */
    public static final int PASSWORD_MAX_LENGTH = 255;
    /**
     * Phone max length
     */
    public static final int PHONE_MAX_LENGTH = 40;

    /**
     * Generate a random Customer model without id and active
     * @return a customer model instance
     */
    public static CustomerDomain random() {
        return new CustomerDomain(
                CustomerIdDomainMother.random(),
                FakerMother.randomFirstname(),
                FakerMother.randomLastname(),
                FakerMother.randomEmail(),
                FakerMother.randomPassword(),
                FakerMother.randomInternationalPhoneNumber(),
                Boolean.TRUE
        );
    }

    /**
     * Generate a random Customer model without id and active
     * @return a customer model instance
     */
    public static CustomerDomain randomNewCustomer() {
        return new CustomerDomain(
            null,
            FakerMother.randomFirstname(),
            FakerMother.randomLastname(),
            FakerMother.randomEmail(),
            FakerMother.randomPassword(),
            FakerMother.randomInternationalPhoneNumber(),
            Boolean.TRUE
        );
    }

    /**
     * Generate a customer with all properties nullables
     * @return a customer model instance
     */
    public static CustomerDomain nullableNewCustomer() {
        return new CustomerDomain(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    /**
     * Generate a customer with invalids max length
     * @return a customer model instance
     */
    public static CustomerDomain invalidMaxLengthNewCustomer() {
        return new CustomerDomain(
                null,
                FakerMother.randomText(FIRSTNAME_MAX_LENGTH + 1),
                FakerMother.randomText(LASTNAME_MAX_LENGTH + 1),
                FakerMother.randomText(EMAIL_MAX_LENGTH + 1),
                FakerMother.randomText(PASSWORD_MAX_LENGTH + 1),
                FakerMother.randomText(PHONE_MAX_LENGTH + 1),
                Boolean.TRUE
        );
    }

    /**
     * Generate a random Customer model with invalid email
     * @return a customer model instance
     */
    public static CustomerDomain invalidEmailNewCustomer() {
        return new CustomerDomain(
                null,
                FakerMother.randomFirstname(),
                FakerMother.randomLastname(),
                FakerMother.randomUsername(),
                FakerMother.randomPassword(),
                FakerMother.randomInternationalPhoneNumber(),
                Boolean.TRUE
        );
    }
}
