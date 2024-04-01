package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.infrastructure.entities.Product;
import com.perficient.shoppingcart.infrastructure.mother.ProductDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductIdDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductDomainRepositoryImplTest {
    private ProductDomainRepositoryImpl productDomainRepository;

    @Mock
    private ProductCacheRepository productCacheRepository;


    @BeforeEach
    void init() {
        productDomainRepository = new ProductDomainRepositoryImpl(productCacheRepository);
    }

    @Test
    void getProductFromStock() {
        var product = ProductMother.random();
        var productIdDomain = ProductIdDomainMother.random();

        when(productCacheRepository.findByIdFromCache(anyString())).thenReturn(Optional.of(product));

        var actual = productDomainRepository.getProductFromStock(productIdDomain);

        assertTrue(actual.isPresent());

        var productDomain = actual.get();
        assertNotNull(productDomain.getProductIdDomain());
        assertEquals(product.getCode(), productDomain.getCode());
        assertEquals(product.getName(), productDomain.getName());
        assertEquals(product.getStock(), productDomain.getStock());
        assertEquals(product.getUnitPrice(), productDomain.getUnitPrice());
        assertEquals(product.isActive(), productDomain.getActive());
    }

    @Test
    void updateProductInStock() {
        var productDomain = ProductDomainMother.random();

        productDomainRepository.updateProductInStock(productDomain);

        verify(productCacheRepository, times(1)).updateProductInCache(any(Product.class));
    }
}
