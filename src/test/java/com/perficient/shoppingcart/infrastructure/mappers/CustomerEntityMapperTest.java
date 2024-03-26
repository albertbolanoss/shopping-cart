package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.infrastructure.mother.CustomerModelMother;
import com.perficient.shoppingcart.model.CustomerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerEntityMapperTest {
    private CustomerEntityMapper customerEntityMapper;

    @BeforeEach
    public void init() {
        this.customerEntityMapper = new CustomerEntityMapperImpl();
    }

    @Test
    void convert() {
        var customerModel = CustomerModelMother.randomNewCustomer();
        var actual = this.customerEntityMapper.convert(customerModel);

        assertNotNull(actual);

    }

//    @Test
//    void convertNoVali() {
//        var customerModel = new CustomerModel(
//                "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890",
//                "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890",
//                null,
//                null
//        );
//        var actual = this.customerEntityMapper.convert(customerModel);
//
//        assertNotNull(actual);
//
//    }
}
