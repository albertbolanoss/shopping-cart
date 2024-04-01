package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.FakerMother;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemModelApiMapperTest {
    @Test
    void fromDomain() {
        var cartItemDomain = CartItemDomainMother.random();

        ConcurrentHashMap<String, CartItemDomain> cart = new ConcurrentHashMap<>();
        cart.put(FakerMother.faker.random().hex(36), CartItemDomainMother.random());
        cart.put(FakerMother.faker.random().hex(36), CartItemDomainMother.random());
        cart.put(FakerMother.faker.random().hex(36), CartItemDomainMother.random());

        var actual = ItemModelApiMapper.fromDomain(cart);

        assertNotNull(actual);
        assertEquals(cart.size(), actual.size());
    }

    @Test
    void fromDomainNull() {

        var actual = ItemModelApiMapper.fromDomain(null);

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }
}
