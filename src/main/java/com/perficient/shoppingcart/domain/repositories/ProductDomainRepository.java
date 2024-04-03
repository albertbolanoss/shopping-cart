package com.perficient.shoppingcart.domain.repositories;

import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;

import java.util.Optional;

public interface ProductDomainRepository {
    Optional<ProductDomain> getProductById(ProductIdDomain productIdDomain);

    void updateStockQuantity(ProductIdDomain productIdDomain, Integer quantity);

    Integer getStockQuantity(ProductIdDomain productIdDomain);
}
