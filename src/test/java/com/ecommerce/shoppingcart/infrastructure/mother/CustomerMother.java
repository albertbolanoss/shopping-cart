package com.ecommerce.shoppingcart.infrastructure.mother;

import com.ecommerce.shoppingcart.infrastructure.entities.Customer;

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
                FakerMother.getFaker().random().hex(36),
                FakerMother.getFaker().name().firstName(),
                FakerMother.getFaker().name().lastName(),
                FakerMother.getFaker().internet().emailAddress(),
                FakerMother.getFaker().internet().password(),
                FakerMother.getFaker().internet().username(),
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
