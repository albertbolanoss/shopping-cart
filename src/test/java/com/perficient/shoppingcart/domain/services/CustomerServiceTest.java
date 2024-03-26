package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.infrastructure.mother.CustomerMother;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerServiceTest {
//    @Autowired
//    private CustomerService customerService;
//
//    @Test
//    void registerSuccessfully() {
//        var customer = CustomerMother.randomNewCustomer();
//        customerService.register(customer);
//    }
//
//    @Test
//    void registerNullable() {
//        assertThrows(ConstraintViolationException.class,
//                () -> customerService.register(null));
//    }
//
//    @Test
//    void registerNullableNewCustomer() {
//        var customer = CustomerMother.nullableNewCustomer();
//        assertThrows(ConstraintViolationException.class,
//                () -> customerService.register(customer));
//    }
//
//    @Test
//    void registerInvalidMaxLengthNewCustomer() {
//        var customer = CustomerMother.invalidMaxLengthNewCustomer();
//        assertThrows(ConstraintViolationException.class,
//                () -> customerService.register(customer));
//    }
}
