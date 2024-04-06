package com.ecommerce.shoppingcart.infrastructure.repository;

import com.ecommerce.shoppingcart.infrastructure.entities.Product;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Product repository
 */
public interface ProductRepository extends CrudRepository<Product, UUID> {
    /**
     * Find a product by id
     * @param id the product id
     * @return an optional of product
     */
    @Query("SELECT p from Product p WHERE p.id = :id")
    Optional<Product> getById(String id);

}
