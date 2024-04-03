package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.domain.valueobjects.CustomerIdDomain;

/**
 * Generate Customer Id Data
 */
public class CustomerIdDomainMother {
    /**
     * First name max length
     */
    private static final int CUSTOMER_ID_MAX_LENGTH = 36;
    /**
     * Generate a random CustomerId
     * @return CustomerId model instance
     */
    public static CustomerIdDomain random() {
        return new CustomerIdDomain(FakerMother.getFaker().random().hex(CUSTOMER_ID_MAX_LENGTH));
    }
}
