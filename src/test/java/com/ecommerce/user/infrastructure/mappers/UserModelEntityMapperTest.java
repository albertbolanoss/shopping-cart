package com.ecommerce.user.infrastructure.mappers;

import com.ecommerce.user.infrastructure.mother.UserDomainMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserModelEntityMapperTest {
    @Test
    void convertFromDomain() {
        var customer = UserDomainMother.randomNewUser();

        var actual = UserEntityMapper.convertFromDomain(customer);

        assertNotNull(actual);
        assertNull(actual.getId());
        assertEquals(customer.getEmail(), actual.getEmail());
        assertEquals(customer.getPassword(), actual.getPassword());
        assertEquals(customer.getActive(), actual.isActive());
        assertEquals(customer.getPhone(), actual.getPhone());
        assertEquals(customer.getFirstName(), actual.getFirstName());
        assertEquals(customer.getLastName(), actual.getLastName());
    }

    @Test
    void convertFromDomainNullable() {
        var actual = UserEntityMapper.convertFromDomain(null);

        assertNull(actual);
    }

    @Test
    void convertFromDomainValuesNullables() {
        var customerDomain = UserDomainMother.nullableNewCustomer();
        var actual = UserEntityMapper.convertFromDomain(customerDomain);

        assertNotNull(actual);
        assertNull(actual.getId());
        assertNull(actual.getEmail());
        assertNull(actual.getPassword());
        assertNull(actual.getLastName());
        assertNull(actual.getFirstName());
        assertNull(actual.getPhone());
    }

}
