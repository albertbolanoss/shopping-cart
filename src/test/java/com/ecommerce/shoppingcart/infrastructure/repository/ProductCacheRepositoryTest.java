package com.ecommerce.shoppingcart.infrastructure.repository;

import com.ecommerce.shared.infrastructure.mother.FakerMother;
import com.ecommerce.shoppingcart.infrastructure.mother.ProductMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductCacheRepositoryTest {
    private ProductCacheRepository productCacheRepository;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void init() {
        productCacheRepository = new ProductCacheRepository(productRepository);
    }

    @Test
    void getStockQuantityWhenProductIsFound() {
        var product = ProductMother.random();
        var expectedStock = product.getStock();

        when(productRepository.getById(anyString())).thenReturn(Optional.of(product));

        var actualStockQuantity = productCacheRepository.getStockQuantity(product.getId());

        assertEquals(expectedStock, actualStockQuantity);
    }

    @Test
    void getStockQuantityWhenProductNotFound() {
        var productId = FakerMother.getFaker().random().hex();
        var expectedStock = 0;

        when(productRepository.getById(anyString())).thenReturn(Optional.empty());

        var actualStockQuantity = productCacheRepository.getStockQuantity(productId);

        assertEquals(expectedStock, actualStockQuantity);
    }

    @Test
    void updateStockQuantityWhenProductIsPresent() {
        var product = ProductMother.random();
        var expectedStockQuantity = product.getStock();

        var actualStockQuantity = productCacheRepository.updateStockQuantity(product.getId(), product.getStock());

        assertEquals(expectedStockQuantity, actualStockQuantity);
    }

    @Test
    void updateStockQuantityWhenProductIsNotPresent() {
        var product = ProductMother.random();
        var expectedStockQuantity = 0;

        var actualStockQuantity = productCacheRepository.updateStockQuantity(product.getId(), null);

        assertEquals(expectedStockQuantity, actualStockQuantity);
    }

    @Test
    void deleteStockQuantity() {
        var productId = FakerMother.getFaker().random().hex();

        productCacheRepository.deleteStockQuantity(productId);
    }
}
