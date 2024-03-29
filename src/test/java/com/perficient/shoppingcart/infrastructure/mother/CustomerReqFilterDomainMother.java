package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.domain.valueobjects.CustomerReqFilterDomain;

import java.util.Arrays;

/**
 * Generate data for Customer Req filter domain Mother
 */
public class CustomerReqFilterDomainMother {
    /**
     * Generate a random Customer Req filter domain
     * @return a Customer Req filter domain
     */
    public static CustomerReqFilterDomain random() {
        return new CustomerReqFilterDomain(
            FakerMother.randomFirstname(),
            FakerMother.randomLastname(),
            FakerMother.randomEmail(),
            0,
            100,
            Arrays.asList("field1_desc","field2_desc")
        );
    }
}
