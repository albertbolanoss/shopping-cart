package com.ecommerce.shoppingcart.infrastructure.mother;

import com.ecommerce.shoppingcart.domain.valueobjects.CustomerReqFilterDomain;

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
            FakerMother.getFaker().name().firstName(),
            FakerMother.getFaker().name().lastName(),
            FakerMother.getFaker().internet().emailAddress(),
            0,
            100,
            Arrays.asList("field1_desc","field2_desc")
        );
    }
}
