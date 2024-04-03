package com.perficient.shoppingcart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShoppingCarApplicationTest {
    private ShoppingCarApplication shoppingCarApplication;

    @BeforeEach
    void init() {
        shoppingCarApplication = new ShoppingCarApplication();
    }

    @Test
    void shallowEtagHeaderFilter() {
        var shallowEtagHeaderFilter = shoppingCarApplication.shallowEtagHeaderFilter();

        assertNotNull(shallowEtagHeaderFilter);
    }
}
