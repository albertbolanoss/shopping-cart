package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.infrastructure.mother.ProductIdDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductMother;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductDomainRepositoryImplTest {
    @InjectMocks
    private ProductDomainRepositoryImpl productDomainRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void findById() {
        var product = ProductMother.random();
        var productIdDomain = ProductIdDomainMother.random();

        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

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

//    @Test
//    public void findByIdCacheable() {
//        var product = ProductMother.random();
//        var productIdDomain = ProductIdDomainMother.random();
//
//        when(productRepository.findById(any())).thenReturn(Optional.of(product));
//
//        productDomainRepository.findById(productIdDomain);
//        productDomainRepository.findById(productIdDomain);
//
//        verify(productRepository, times(1)).findById(any(UUID.class));
//
//    }
}
