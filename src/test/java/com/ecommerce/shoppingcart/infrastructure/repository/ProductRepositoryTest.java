package com.ecommerce.shoppingcart.infrastructure.repository;


import com.ecommerce.shoppingcart.infrastructure.entities.Product;
import com.ecommerce.shoppingcart.infrastructure.mother.ProductMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void init() {
        product = productRepository.save(ProductMother.random());
    }

    @AfterEach
    void cleanUp() {
        productRepository.deleteAll();
    }


    @Test
    void findById() {
        var actualProduct = productRepository.getById(product.getId());

        assertTrue(actualProduct.isPresent());
        assertEquals(product.getId(), actualProduct.get().getId());
    }

    @Test
    void findByIdNoFound() {
        var actualProduct = productRepository.getById(UUID.randomUUID().toString());

        assertTrue(actualProduct.isEmpty());
    }
}
