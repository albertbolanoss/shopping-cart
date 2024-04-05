package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.infrastructure.mother.ProductDomainMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductMapperTest {
    @Test
    void fromDomain() {
        var productDomain = ProductDomainMother.random();

        var actual = ProductMapper.fromDomain(productDomain);

        assertNotNull(actual);
        assertEquals(productDomain.getProductIdDomain().getId(), actual.getId());
        assertEquals(productDomain.getName(), actual.getName());
        assertEquals(productDomain.getUnitPrice(), actual.getUnitPrice());
        assertEquals(productDomain.getCode(), actual.getCode());
        assertEquals(productDomain.getStock(), actual.getStock());
    }

    @Test
    void fromDomainFromNull() {
        var actual = ProductMapper.fromDomain(null);

        assertNull(actual);
    }
}
