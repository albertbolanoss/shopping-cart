package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.infrastructure.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Manage the related product cache
 */
@Repository
public class ProductCacheRepository {
    /**
     * Product repository
     */
    private final  ProductRepository productRepository;

    @Autowired
    public ProductCacheRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get the product information from cache
     * @param id the product id
     * @return a Product entity
     */
    @Cacheable(value="ProductInStock", key="#id", unless = "#result == null")
    Optional<Product> findByIdFromCache(String id) {
        return productRepository.getById(id);
    }

    /**
     * Update a product in cache
     * @param product the product to update
     * @return an optional of product entity
     */
    @CachePut(value="ProductInStock", key="#product.id")
    public Optional<Product> updateProductInCache(Product product) {
        return Optional.ofNullable(product);
    }
}
