package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.infrastructure.entities.Customer;

/**
 * Generate customer entity data
 */
public class CustomerMother {
    /**
     * Generate a random entity customer
     * @return a customer entity
     */
    public static Customer random() {
        return new Customer(
                FakerMother.randomHex(36),
                FakerMother.randomFirstname(),
                FakerMother.randomLastname(),
                FakerMother.randomEmail(),
                FakerMother.randomPassword(),
                FakerMother.randomInternationalPhoneNumber(),
                Boolean.TRUE
        );
    }

    /**
     * Generate a random entity customer
     * @return a customer entity
     */
    public static Customer nullable() {
        return new Customer(
                null,
                null,
                null,
                null,
                null,
                null,
                Boolean.FALSE
        );
    }
}
