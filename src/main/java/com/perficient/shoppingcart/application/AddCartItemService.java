package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.exceptions.NotExistException;
import com.perficient.shoppingcart.domain.exceptions.ProductNotAvailableException;
import com.perficient.shoppingcart.domain.services.CartService;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.ConcurrentMap;

/**
 * Add cart item service application
 */
@Service
public class AddCartItemService {
    /**
     * Cart service domain
     */
    private final CartService cartService;

    @Autowired
    public AddCartItemService(CartService productService) {
        this.cartService = productService;
    }

    /**
     * Add a product item to the stock
     * @param productIdDomain the product id domain
     * @param cartItemsDomain the customer cart items domain
     */
    public void addItemToCart(ProductIdDomain productIdDomain,
                              ConcurrentMap<String, CartItemDomain> cartItemsDomain) {
        try {
            cartService.addItemToCart(productIdDomain, cartItemsDomain);
        } catch (NotExistException | ProductNotAvailableException ex) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
