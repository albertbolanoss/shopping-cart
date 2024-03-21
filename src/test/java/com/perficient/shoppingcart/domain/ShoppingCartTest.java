package com.perficient.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ShoppingCartTest {

    @Test
    @DisplayName("Should create a cart with no items")
    void shouldCreateACartWithNoItems() {
        assertTrue(new ShoppingCart().getItems().isEmpty(), "empty cart");
    }
}
