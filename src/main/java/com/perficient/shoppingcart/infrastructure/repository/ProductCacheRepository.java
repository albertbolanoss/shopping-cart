package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.infrastructure.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Manage the related product cache
 */
@Repository
@Slf4j
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
    Integer getStockQuantity(String id) {
        return productRepository.getById(id).map(Product::getStock)
                .orElse(0);
    }

    /**
     * Update a product in cache
     * @param id the product id
     * @param quantity the quantity
     * @return an optional of product entity
     */
    @CachePut(value="ProductInStock", key="#id")
    public Integer updateStockQuantity(String id, Integer quantity) {
        log.debug("Updating Stock quantity of the product id: {}", id);
        return Optional.ofNullable(quantity).orElse(0);
    }

    /**
     * Remove the product in the stock
     * @param id the id product
     */
    @CacheEvict(value="ProductInStock", key="#id")
    public void deleteStockQuantity(String id) {
        log.debug("Remove Stock quantity of the product id: {}", id);
    }
}
