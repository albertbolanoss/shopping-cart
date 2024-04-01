package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.infrastructure.entities.Product;
import com.perficient.shoppingcart.infrastructure.mother.ProductMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductCacheRepositoryTest {
    private ProductCacheRepository productCacheRepository;
    @Mock
    private ProductRepository productRepository;

    private final Map<String, Optional<Product>> cache = new HashMap<>();

    @BeforeEach
    void init() {
        productCacheRepository = new ProductCacheRepository(productRepository);
    }

    @Test
    void findByIdFromCache() {
        var expectedProduct = ProductMother.random();

        when(productRepository.getById(anyString())).thenReturn(Optional.of(expectedProduct));

        var actualProduct = productCacheRepository.findByIdFromCache(expectedProduct.getId());

        assertTrue(actualProduct.isPresent());
        assertEquals(expectedProduct.getId(), actualProduct.get().getId());
    }

    @Test
    void updateProductInCache() {
        var expectedProduct = ProductMother.random();

        var actualProduct = productCacheRepository.updateProductInCache(expectedProduct);

        assertTrue(actualProduct.isPresent());
        assertEquals(expectedProduct.getId(), actualProduct.get().getId());
    }
}
