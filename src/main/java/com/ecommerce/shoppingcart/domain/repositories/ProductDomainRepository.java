package com.ecommerce.shoppingcart.domain.repositories;

import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.ProductDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.ProductIdDomain;

import java.util.List;
import java.util.Optional;

public interface ProductDomainRepository {
    Optional<ProductDomain> getProductById(ProductIdDomain productIdDomain);

    void updateStockQuantity(List<CartItemDomain> cartItemsDomains);

    Integer getStockQuantity(ProductIdDomain productIdDomain);
}
