package com.ecommerce.user.infrastructure.mother;

import com.ecommerce.user.infrastructure.entities.User;
import com.ecommerce.shared.infrastructure.mother.FakerMother;

/**
 * Generate customer entity data
 */
public class UserMother {
    /**
     * Generate a random entity customer
     * @return a customer entity
     */
    public static User random() {
        return new User(
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
    public static User nullable() {
        return new User(
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
