package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.model.Cart;
import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * Add cart item service application
 */
@Service
public class AddCartItemApp {
    /**
     * Cart service domain
     */
    private final ProductDomainRepository productDomainRepository;

    @Autowired
    public AddCartItemApp(ProductDomainRepository productDomainRepository) {
        this.productDomainRepository = productDomainRepository;
    }

    /**
     * Add item
     * @param productIdDomain the product id domain
     * @param cartItemsDomain the cart items domain
     */
    public CartItemDomain addItem(ProductIdDomain productIdDomain, List<CartItemDomain> cartItemsDomain) {
        var productDomain = productDomainRepository.getProductById(productIdDomain).orElseThrow(
                    () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Product was not found"));
        var cart = new Cart(cartItemsDomain);

        return cart.addItem(productDomain);

    }
}
