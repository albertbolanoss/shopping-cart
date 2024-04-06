package com.ecommerce.shoppingcart.infrastructure.mappers;

import com.ecommerce.customer.infrastructure.mappers.CustomerEntityMapper;
import com.ecommerce.shoppingcart.infrastructure.mother.CustomerDomainMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CustomerDomainEntityMapperTest {
    @Test
    void convertFromDomain() {
        var customer = CustomerDomainMother.randomNewCustomer();

        var actual = CustomerEntityMapper.convertFromDomain(customer);

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
        var actual = CustomerEntityMapper.convertFromDomain(null);

        assertNull(actual);
    }

    @Test
    void convertFromDomainValuesNullables() {
        var customerDomain = CustomerDomainMother.nullableNewCustomer();
        var actual = CustomerEntityMapper.convertFromDomain(customerDomain);

        assertNotNull(actual);
        assertNull(actual.getId());
        assertNull(actual.getEmail());
        assertNull(actual.getPassword());
        assertNull(actual.getLastName());
        assertNull(actual.getFirstName());
        assertNull(actual.getPhone());
    }

}
