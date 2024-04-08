package com.ecommerce.user.infrastructure.mother;


import com.ecommerce.user.domain.valueobjects.UserPageDomain;
import com.ecommerce.shared.infrastructure.mother.PageResponseDomainMother;

import java.util.Arrays;

/**
 * Generate a customer page domain
 */
public class UserPageDomainMother {
    /**
     * Generate a random customer page domain
     * @return  a Customer Page Domain
     */
    public static UserPageDomain random() {
        var customers = Arrays.asList(UserDomainMother.random(), UserDomainMother.random());
        var pageResponseDomain = PageResponseDomainMother.random();
        var customerReqFilterDomain = UserReqFilterDomainMother.random();

        return new UserPageDomain(customers, pageResponseDomain, customerReqFilterDomain);
    }
}
