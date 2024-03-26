package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.domain.valueobjects.CustomerId;

/**
 * Generate Customer Id Data
 */
public class CustomerIdMother {
    /**
     * Generate a random CustomerId
     * @return CustomerId model instance
     */
    public static CustomerId random() {
        return new CustomerId(FakerMother.randomHex(36));
    }
}
