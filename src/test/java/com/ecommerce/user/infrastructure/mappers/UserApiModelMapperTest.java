package com.ecommerce.user.infrastructure.mappers;

import com.ecommerce.user.infrastructure.mother.UserDomainMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserApiModelMapperTest {
    @Test
    void convertFromDomain() {
        var UserDomain = UserDomainMother.random();

        var actual = UserApiModelMapper.convertFromDomain(UserDomain);

        assertNotNull(actual);
        assertNotNull(actual.getId());

        assertEquals(UserDomain.getEmail(), actual.getEmail());
        assertEquals(UserDomain.getPhone(), actual.getPhone());
        assertEquals(UserDomain.getFirstName(), actual.getFirstName());
        assertEquals(UserDomain.getLastName(), actual.getLastName());

    }
}
