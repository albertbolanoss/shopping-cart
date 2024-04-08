package com.ecommerce.user.infrastructure.mappers;

import com.ecommerce.user.infrastructure.mother.UserMother;
import com.ecommerce.user.infrastructure.mother.AddUserReqMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDomainDomainMapperTest {

    @Test
    void convertFromAddUserReq() {
        var addUserReq = AddUserReqMother.random();

        var actual = UserDomainMapper.convertFromARequest(addUserReq);

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
    void convertFromAddUserReqNullable() {
        var actual = UserDomainMapper.convertFromARequest(null);

        assertNull(actual);
    }

    @Test
    void convertFromAddUserReqValuesNullables() {
        var addUserReq = AddUserReqMother.nullable();
        var actual = UserDomainMapper.convertFromARequest(addUserReq);

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
        var customer = UserMother.random();

        var actual = UserDomainMapper.convertFromEntity(customer);

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
        var actual = UserDomainMapper.convertFromEntity(null);

        assertNull(actual);
    }

    @Test
    void convertFromEntityValuesNullables() {
        var customer = UserMother.nullable();

        var actual = UserDomainMapper.convertFromEntity(customer);

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
