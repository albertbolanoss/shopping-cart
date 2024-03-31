package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.infrastructure.entities.Product;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCacheRepository {
    @CachePut(value="ProductInStock", key="#product.id")
    public Optional<Product> updateProductCache(Product product) {
        return Optional.ofNullable(product);
    }
}
