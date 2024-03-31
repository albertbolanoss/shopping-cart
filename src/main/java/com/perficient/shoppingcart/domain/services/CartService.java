package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.exceptions.NotExistException;
import com.perficient.shoppingcart.domain.exceptions.ProductNotAvailableException;
import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Product service domain
 */
@Service
public class CartService {
    /**
     * Product domain repository
     */
    private final ProductDomainRepository productDomainRepository;

    @Autowired
    public CartService(ProductDomainRepository productDomainRepository) {
        this.productDomainRepository = productDomainRepository;
    }

    /**
     * Get a product items from the stock
     * @param productIdDomain the product id domain
     * @return a
     */
    public CartItemDomain getItemFromStock(ProductIdDomain productIdDomain) {
        var currentProductDomain = productDomainRepository.getProductFromStock(productIdDomain)
                .orElseThrow(() -> new NotExistException(
                    String.format("The product (%s) does not exist", productIdDomain.getId())));

        if (currentProductDomain.getStock() <= 0) {
            throw new ProductNotAvailableException(
                    String.format("The product (%s) is not available in the stock", productIdDomain.getId()));
        }

        var availableInStock = currentProductDomain.getStock() - 1;
        /*
        var productDomain = new ProductDomain(
                currentProductDomain.getProductIdDomain(),
                currentProductDomain.getCode(),
                currentProductDomain.getName(),
                currentProductDomain.getUnitPrice(),
                availableInStock,
                currentProductDomain.getActive()
        );

        productDomainRepository.updateProductInStock(productId);
        */

        return new CartItemDomain(
                currentProductDomain.getStock() + 1,
                currentProductDomain.getUnitPrice()
        );
    }
}
