package com.ecommerce;

import com.ecommerce.shared.infrastructure.config.api.hateoas.HateoasConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HateoasConfigTest {
    private HateoasConfig hateoasConfig;

    @BeforeEach
    void init() {
        hateoasConfig = new HateoasConfig();
    }

    @Test
    void shallowEtagHeaderFilter() {
        var shallowEtagHeaderFilter = hateoasConfig.shallowEtagHeaderFilter();

        assertNotNull(shallowEtagHeaderFilter);
    }
}
