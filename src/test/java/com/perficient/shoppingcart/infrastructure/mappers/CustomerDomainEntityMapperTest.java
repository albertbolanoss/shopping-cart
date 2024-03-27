package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.infrastructure.mother.CustomerDomainMother;
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

}
