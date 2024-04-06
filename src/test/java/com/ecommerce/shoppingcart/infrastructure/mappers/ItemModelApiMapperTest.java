package com.ecommerce.shoppingcart.infrastructure.mappers;

import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.ecommerce.shoppingcart.infrastructure.mother.FakerMother;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemModelApiMapperTest {
    @Test
    void fromDomain() {
        var cartItemDomain = CartItemDomainMother.random();

        ConcurrentHashMap<String, CartItemDomain> cart = new ConcurrentHashMap<>();
        cart.put(FakerMother.getFaker().random().hex(36), CartItemDomainMother.random());
        cart.put(FakerMother.getFaker().random().hex(36), CartItemDomainMother.random());
        cart.put(FakerMother.getFaker().random().hex(36), CartItemDomainMother.random());

        var actual = ItemModelApiMapper.fromConcurrentMapCart(cart);

        assertNotNull(actual);
        assertEquals(cart.size(), actual.size());
    }

    @Test
    void fromDomainNull() {

        var actual = ItemModelApiMapper.fromConcurrentMapCart(null);

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }
}
