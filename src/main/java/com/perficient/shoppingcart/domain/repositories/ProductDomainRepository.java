package com.perficient.shoppingcart.domain.repositories;

import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;

public interface ProductDomainRepository {
    ProductDomain getProductFromStock(ProductIdDomain productIdDomain);

    void updateProductInStock(ProductDomain productDomain);
}
