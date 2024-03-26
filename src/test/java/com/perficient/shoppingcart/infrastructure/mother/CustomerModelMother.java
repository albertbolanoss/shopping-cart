package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.model.CustomerModel;

/**
 * Genera random data for Customer Model
 */
public class CustomerModelMother {
    /**
     * Generate a random Customer model without id and active
     * @return a customer model instance
     */
    public static CustomerModel randomNewCustomer() {
        return new CustomerModel(
            FakerMother.randomFirstname(100),
            FakerMother.randomLastname(100),
            FakerMother.randomEmail(),
            FakerMother.randomPassword()
        );
    }
}
