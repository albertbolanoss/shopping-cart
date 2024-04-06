package com.ecommerce.shoppingcart.infrastructure.mappers;

import com.ecommerce.customer.infrastructure.mappers.CustomerApiModelMapper;
import com.ecommerce.shoppingcart.infrastructure.mother.CustomerDomainMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerApiModelMapperTest {
    @Test
    void convertFromDomain() {
        var customerDomain = CustomerDomainMother.random();

        var actual = CustomerApiModelMapper.convertFromDomain(customerDomain);

        assertNotNull(actual);
        assertNotNull(actual.getId());

        assertEquals(customerDomain.getEmail(), actual.getEmail());
        assertEquals(customerDomain.getPhone(), actual.getPhone());
        assertEquals(customerDomain.getFirstName(), actual.getFirstName());
        assertEquals(customerDomain.getLastName(), actual.getLastName());

    }
}
