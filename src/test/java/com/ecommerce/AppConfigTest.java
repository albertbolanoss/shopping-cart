package com.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppConfigTest {
    private AppConfig appConfig;

    @BeforeEach
    void init() {
        appConfig = new AppConfig();
    }

    @Test
    void shallowEtagHeaderFilter() {
        var shallowEtagHeaderFilter = appConfig.shallowEtagHeaderFilter();

        assertNotNull(shallowEtagHeaderFilter);
    }
}
