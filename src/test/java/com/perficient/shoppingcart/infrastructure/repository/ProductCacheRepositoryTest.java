package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.infrastructure.mother.ProductMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductCacheRepositoryTest {
    @Autowired
    private ProductCacheRepository productCacheRepository;
    @MockBean
    private ProductRepository productRepository;

    @Test
    void findByIdFromCache() {
        var expectedProduct = ProductMother.random();

        when(productRepository.getById(anyString())).thenReturn(Optional.of(expectedProduct));

        productCacheRepository.findByIdFromCache(expectedProduct.getId());
        var actualProduct = productCacheRepository.findByIdFromCache(expectedProduct.getId());

        assertTrue(actualProduct.isPresent());
        assertEquals(expectedProduct.getId(), actualProduct.get().getId());
        verify(productRepository, times(1)).getById(anyString());
    }

    @Test
    void updateProductInCache() {
        var expectedProduct = ProductMother.random();

        var actualProduct = productCacheRepository.updateProductInCache(expectedProduct);

        assertTrue(actualProduct.isPresent());
        assertEquals(expectedProduct.getId(), actualProduct.get().getId());
        
    }
}
