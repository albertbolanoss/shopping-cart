package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.domain.valueobjects.Customer;

/**
 * Genera random data for Customer Model
 */
public class CustomerMother {
    /**
     * First name max length
     */
    private static final int FIRSTNAME_MAX_LENGTH = 125;
    /**
     * Last name max length
     */
    private static final int LASTNAME_MAX_LENGTH = 125;
    /**
     * Email max length
     */
    private static final int EMAIL_MAX_LENGTH = 255;
    /**
     * Password max length
     */
    private static final int PASSWORD_MAX_LENGTH = 255;
    /**
     * Phone max length
     */
    private static final int PHONE_MAX_LENGTH = 40;

    /**
     * Generate a random Customer model without id and active
     * @return a customer model instance
     */
    public static Customer randomNewCustomer() {
        return new Customer(
            FakerMother.randomFirstname(FIRSTNAME_MAX_LENGTH),
            FakerMother.randomLastname(LASTNAME_MAX_LENGTH),
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
    public static Customer nullableNewCustomer() {
        return new Customer(
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
    public static Customer invalidMaxLengthNewCustomer() {
        return new Customer(
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
    public static Customer invalidEmailNewCustomer() {
        return new Customer(
                FakerMother.randomFirstname(FIRSTNAME_MAX_LENGTH),
                FakerMother.randomLastname(LASTNAME_MAX_LENGTH),
                FakerMother.randomUsername(),
                FakerMother.randomPassword(),
                FakerMother.randomInternationalPhoneNumber(),
                Boolean.TRUE
        );
    }
}