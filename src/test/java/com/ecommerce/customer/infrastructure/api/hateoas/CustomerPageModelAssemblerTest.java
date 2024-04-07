package com.ecommerce.customer.infrastructure.api.hateoas;

import com.ecommerce.customer.infrastructure.mother.CustomerPageDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerPageModelAssemblerTest {
    private CustomerPageModelAssembler customerPageModelAssembler;

    @BeforeEach
    void init() {
        customerPageModelAssembler = new CustomerPageModelAssembler();
    }

    @Test
    void toModel() {
        var customerPageDomain = CustomerPageDomainMother.random();

        var result = customerPageModelAssembler.toModel(customerPageDomain);

        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertNotNull(result.getPageSize());
        assertNotNull(result.getContent());
        assertNotNull(result.getTotalItems());
        assertNotNull(result.getTotalPages());
    }
}
