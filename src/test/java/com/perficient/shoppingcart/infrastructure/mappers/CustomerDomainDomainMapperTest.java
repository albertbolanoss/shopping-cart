package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.infrastructure.mother.AddCustomerReqMother;
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
        assertNull(actual.getCustomerIdDomain());
        assertTrue(actual.getActive());
        assertEquals(addUserReq.getEmail(), actual.getEmail());
        assertEquals(addUserReq.getPassword(), actual.getPassword());
        assertEquals(addUserReq.getPhone(), actual.getPhone());
        assertEquals(addUserReq.getFirstName(), actual.getFirstName());
        assertEquals(addUserReq.getLastName(), actual.getLastName());
    }
}
