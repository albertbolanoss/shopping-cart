package com.ecommerce.customer.infrastructure.mappers;

import com.ecommerce.customer.infrastructure.mother.CustomerMother;
import com.ecommerce.customer.infrastructure.mother.AddCustomerReqMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerDomainDomainMapperTest {

    @Test
    void convertFromARequest() {
        var addUserReq = AddCustomerReqMother.random();

        var actual = CustomerDomainMapper.convertFromARequest(addUserReq);

        assertNotNull(actual);
        assertNotNull(actual.getCustomerId());
        assertNull(actual.getCustomerId().getId());
        assertTrue(actual.getActive());
        assertEquals(addUserReq.getEmail(), actual.getEmail());
        assertEquals(addUserReq.getPassword(), actual.getPassword());
        assertEquals(addUserReq.getPhone(), actual.getPhone());
        assertEquals(addUserReq.getFirstName(), actual.getFirstName());
        assertEquals(addUserReq.getLastName(), actual.getLastName());
    }

    @Test
    void convertFromARequestNullable() {
        var actual = CustomerDomainMapper.convertFromARequest(null);

        assertNull(actual);
    }

    @Test
    void convertFromARequestValuesNullables() {
        var addUserReq = AddCustomerReqMother.nullable();
        var actual = CustomerDomainMapper.convertFromARequest(addUserReq);

        assertNotNull(actual);
        assertNotNull(actual.getCustomerId());
        assertNull(actual.getCustomerId().getId());
        assertNull(actual.getEmail());
        assertNull(actual.getPassword());
        assertNull(actual.getLastName());
        assertNull(actual.getFirstName());
        assertNull(actual.getPhone());
        assertTrue(actual.getActive());
    }

    @Test
    void convertFromEntity() {
        var customer = CustomerMother.random();

        var actual = CustomerDomainMapper.convertFromEntity(customer);

        assertNotNull(actual);
        assertNotNull(actual.getCustomerId());
        assertEquals(customer.getId(), actual.getCustomerId().getId());
        assertEquals(customer.isActive(), actual.getActive());
        assertEquals(customer.getEmail(), actual.getEmail());
        assertEquals(customer.getPassword(), actual.getPassword());
        assertEquals(customer.getPhone(), actual.getPhone());
        assertEquals(customer.getFirstName(), actual.getFirstName());
        assertEquals(customer.getLastName(), actual.getLastName());
    }

    @Test
    void convertFromEntityNullable() {
        var actual = CustomerDomainMapper.convertFromEntity(null);

        assertNull(actual);
    }

    @Test
    void convertFromEntityValuesNullables() {
        var customer = CustomerMother.nullable();

        var actual = CustomerDomainMapper.convertFromEntity(customer);

        assertNotNull(actual);
        assertNotNull(actual.getCustomerId());
        assertNull(actual.getCustomerId().getId());
        assertNull(actual.getEmail());
        assertNull(actual.getPassword());
        assertNull(actual.getLastName());
        assertNull(actual.getFirstName());
        assertNull(actual.getPhone());
        assertEquals(customer.isActive(), actual.getActive());
    }
}
