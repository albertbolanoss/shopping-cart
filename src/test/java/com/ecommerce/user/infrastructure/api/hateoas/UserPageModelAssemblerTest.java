package com.ecommerce.user.infrastructure.api.hateoas;

import com.ecommerce.user.infrastructure.mother.UserPageDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserPageModelAssemblerTest {
    private UserPageModelAssembler userPageModelAssembler;

    @BeforeEach
    void init() {
        userPageModelAssembler = new UserPageModelAssembler();
    }

    @Test
    void toModel() {
        var customerPageDomain = UserPageDomainMother.random();

        var result = userPageModelAssembler.toModel(customerPageDomain);

        assertNotNull(result);
        assertNotNull(result.getLinks());
        assertNotNull(result.getPageSize());
        assertNotNull(result.getContent());
        assertNotNull(result.getTotalItems());
        assertNotNull(result.getTotalPages());
    }
}
