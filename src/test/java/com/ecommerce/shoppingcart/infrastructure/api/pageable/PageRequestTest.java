package com.ecommerce.shoppingcart.infrastructure.api.pageable;

import com.ecommerce.shoppingcart.infrastructure.api.pageable.PageRequestCreator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PageRequestTest {
    @Test
    void create() {
        var pageNumber = 1;
        var pageSize = 10;
        var sortFieldsWithDirection = Arrays.asList("field1_desc", "field2_asc");

        var actual = PageRequestCreator.create(pageNumber, pageSize, sortFieldsWithDirection);

        assertEquals(pageNumber, actual.getPageNumber());
        assertEquals(pageSize, actual.getPageSize());
        assertFalse(actual.getSort().isEmpty());
        actual.getSort().stream().forEach(sort -> {
            var key = String.format("%s_%s", sort.getProperty(), sort.getDirection()).toLowerCase();
            assertTrue(sortFieldsWithDirection.contains(key));
        });
    }

    @Test
    void createWithNullableSort() {
        var pageNumber = 1;
        var pageSize = 10;
        var actual = PageRequestCreator.create(pageNumber, pageSize, null);

        assertEquals(pageNumber, actual.getPageNumber());
        assertEquals(pageSize, actual.getPageSize());
        assertTrue(actual.getSort().isEmpty());
    }
}
