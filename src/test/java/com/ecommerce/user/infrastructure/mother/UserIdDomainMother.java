package com.ecommerce.user.infrastructure.mother;

import com.ecommerce.user.domain.valueobjects.UserIdDomain;
import com.ecommerce.shared.infrastructure.mother.FakerMother;

/**
 * Generate Customer Id Data
 */
public class UserIdDomainMother {
    /**
     * First name max length
     */
    private static final int CUSTOMER_ID_MAX_LENGTH = 36;
    /**
     * Generate a random CustomerId
     * @return CustomerId model instance
     */
    public static UserIdDomain random() {
        return new UserIdDomain(FakerMother.getFaker().random().hex(CUSTOMER_ID_MAX_LENGTH));
    }
}
