package com.ecommerce.user.infrastructure.mother;

import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import com.ecommerce.shared.infrastructure.mother.FakerMother;

import java.util.Arrays;

/**
 * Generate data for Customer Req filter domain Mother
 */
public class UserReqFilterDomainMother {
    /**
     * Generate a random Customer Req filter domain
     * @return a Customer Req filter domain
     */
    public static UserReqFilterDomain random() {
        return new UserReqFilterDomain(
            FakerMother.getFaker().name().firstName(),
            FakerMother.getFaker().name().lastName(),
            FakerMother.getFaker().internet().emailAddress(),
            0,
            100,
            Arrays.asList("field1_desc","field2_desc")
        );
    }
}
