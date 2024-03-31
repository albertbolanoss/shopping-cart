package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.infrastructure.entities.Product;
import org.hibernate.validator.constraints.UUID;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    @Cacheable(value="ProductInStock", key="#id", unless = "#result == null")
    @Query("SELECT p from Product p WHERE p.id = :id")
    Optional<Product> findByIdFromCache(String id);

}
