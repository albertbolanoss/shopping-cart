package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.infrastructure.mother.AddCustomerReqMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerDomainMapperTest {
    private CustomerDomainMapper customerDomainMapper;

    @BeforeEach
    public void init() {
        this.customerDomainMapper = new CustomerDomainMapperImpl();
    }

    @Test
    void convertFromARequest() {
        var addUserReq = AddCustomerReqMother.random();

        var actual = this.customerDomainMapper.convertFromARequest(addUserReq);

        assertNotNull(actual);
        assertNull(actual.getCustomerId());
        assertTrue(actual.getActive());
        assertEquals(addUserReq.getEmail(), actual.getEmail());
        assertEquals(addUserReq.getPassword(), actual.getPassword());
        assertEquals(addUserReq.getPhone(), actual.getPhone());
        assertEquals(addUserReq.getFirstName(), actual.getFirstName());
        assertEquals(addUserReq.getLastName(), actual.getLastName());
    }
}
