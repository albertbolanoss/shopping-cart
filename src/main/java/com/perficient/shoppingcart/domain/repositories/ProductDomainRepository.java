package com.perficient.shoppingcart.domain.repositories;

import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;

import java.util.Optional;

public interface ProductDomainRepository {
    Optional<ProductDomain> getProductFromStock(ProductIdDomain productIdDomain);
}
