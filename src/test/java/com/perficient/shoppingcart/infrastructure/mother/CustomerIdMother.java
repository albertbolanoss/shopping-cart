package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.domain.valueobjects.CustomerId;

/**
 * Generate Customer Id Data
 */
public class CustomerIdMother {
    /**
     * First name max length
     */
    private static final int CUSTOMER_ID_MAX_LENGTH = 36;
    /**
     * Generate a random CustomerId
     * @return CustomerId model instance
     */
    public static CustomerId random() {
        return new CustomerId(FakerMother.randomHex(CUSTOMER_ID_MAX_LENGTH));
    }
}
