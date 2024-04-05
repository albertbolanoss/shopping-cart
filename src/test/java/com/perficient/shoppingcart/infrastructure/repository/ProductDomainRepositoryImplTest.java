package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.domain.exceptions.NotAvailableInStockException;
import com.perficient.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductIdDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductDomainRepositoryImplTest {
    private ProductDomainRepositoryImpl productDomainRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCacheRepository productCacheRepository;


    @BeforeEach
    void init() {
        productDomainRepository = new ProductDomainRepositoryImpl(productRepository, productCacheRepository);
    }

    @Test
    void getProductById() {
        var product = ProductMother.random();
        var productIdDomain = ProductIdDomainMother.random();

        when(productRepository.getById(anyString())).thenReturn(Optional.of(product));

        var actual = productDomainRepository.getProductById(productIdDomain);

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
        var cartItemsDomain = List.of(CartItemDomainMother.random(),
                CartItemDomainMother.random(), CartItemDomainMother.random());
        var quantity = 200;

        when(productCacheRepository.getStockQuantity(anyString())).thenReturn(quantity);
        when(productCacheRepository.updateStockQuantity(anyString(), any(Integer.class))).thenReturn(quantity);

        productDomainRepository.updateStockQuantity(cartItemsDomain);

        verify(productCacheRepository, times(3)).updateStockQuantity(anyString(), any(Integer.class));
    }

    @Test
    void updateProductInStockWhenNotAvailableInStock() {
        var cartItemsDomain = List.of(CartItemDomainMother.random(),
                CartItemDomainMother.random(), CartItemDomainMother.random());
        var product = ProductMother.random();
        var quantity = 0;

        when(productCacheRepository.getStockQuantity(anyString())).thenReturn(quantity);
        when(productCacheRepository.updateStockQuantity(anyString(), any(Integer.class))).thenReturn(quantity);
        when(productRepository.getById(anyString())).thenReturn(Optional.of(product));

        assertThrows(NotAvailableInStockException.class,
                () -> productDomainRepository.updateStockQuantity(cartItemsDomain));
    }

    @Test
    void updateProductInStockWhenNotAvailableInStockAndNoFoundProduct() {
        var cartItemsDomain = List.of(CartItemDomainMother.random(),
                CartItemDomainMother.random(), CartItemDomainMother.random());
        var quantity = 0;

        when(productCacheRepository.getStockQuantity(anyString())).thenReturn(quantity);
        when(productCacheRepository.updateStockQuantity(anyString(), any(Integer.class))).thenReturn(quantity);
        when(productRepository.getById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotAvailableInStockException.class,
                () -> productDomainRepository.updateStockQuantity(cartItemsDomain));
    }

    @Test
    void getStockQuantity() {
        var productId = ProductIdDomainMother.random();

        when(productCacheRepository.getStockQuantity(anyString())).thenReturn(10);

        productDomainRepository.getStockQuantity(productId);

        verify(productCacheRepository, atLeastOnce()).getStockQuantity(anyString());
    }
}
