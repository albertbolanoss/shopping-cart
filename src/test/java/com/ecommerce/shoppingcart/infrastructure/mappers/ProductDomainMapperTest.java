package com.ecommerce.shoppingcart.infrastructure.mappers;

import com.ecommerce.shoppingcart.infrastructure.mother.ProductMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductDomainMapperTest {
    @Test
    void fromEntity() {
        var product = ProductMother.random();

        var actual = ProductDomainMapper.fromEntity(product);

        assertNotNull(actual);
        assertNotNull(actual.getProductIdDomain());
        assertEquals(product.getCode(), actual.getCode());
        assertEquals(product.getName(), actual.getName());
        assertEquals(product.getStock(), actual.getStock());
        assertEquals(product.getUnitPrice(), actual.getUnitPrice());
        assertEquals(product.isActive(), actual.getActive());
    }
}
