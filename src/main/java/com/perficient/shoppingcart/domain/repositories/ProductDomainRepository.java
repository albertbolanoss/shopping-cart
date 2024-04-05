package com.perficient.shoppingcart.domain.repositories;

import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;

import java.util.List;
import java.util.Optional;

public interface ProductDomainRepository {
    Optional<ProductDomain> getProductById(ProductIdDomain productIdDomain);

    void updateStockQuantity(List<CartItemDomain> cartItemsDomains);

    Integer getStockQuantity(ProductIdDomain productIdDomain);
}
