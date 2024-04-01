package com.perficient.shoppingcart.infrastructure.api.hateoas;

import com.perficient.shoppingcart.infrastructure.mother.CustomerPageDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerPageModelAssemblerTest {
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
