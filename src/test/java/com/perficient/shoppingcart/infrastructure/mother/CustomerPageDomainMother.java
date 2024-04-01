package com.perficient.shoppingcart.infrastructure.mother;


import com.perficient.shoppingcart.domain.valueobjects.CustomerPageDomain;

import java.util.Arrays;

/**
 * Generate a customer page domain
 */
public class CustomerPageDomainMother {
    /**
     * Generate a random customer page domain
     * @return  a Customer Page Domain
     */
    public static CustomerPageDomain random() {
        var customers = Arrays.asList(CustomerDomainMother.random(), CustomerDomainMother.random());
        var pageResponseDomain = PageResponseDomainMother.random();
        var customerReqFilterDomain = CustomerReqFilterDomainMother.random();

        return new CustomerPageDomain(customers, pageResponseDomain, customerReqFilterDomain);
    }
}
