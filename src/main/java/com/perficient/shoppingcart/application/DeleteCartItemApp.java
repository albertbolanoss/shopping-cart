package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.exceptions.CartEmptyException;
import com.perficient.shoppingcart.domain.exceptions.NotExistException;
import com.perficient.shoppingcart.domain.services.CartService;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.ConcurrentMap;

@Service
public class DeleteCartItemApp {
    private final CartService cartService;

    @Autowired
    public DeleteCartItemApp(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Delete an item from cart
     * @param productIdDomain the prodict id domain
     * @param cartItemsDomain the cart items domain
     */
    public void deleteItemFromCart(
            ProductIdDomain productIdDomain,
            ConcurrentMap<String, CartItemDomain> cartItemsDomain) {

        try {
            cartService.deleteItemFromCart(productIdDomain, cartItemsDomain);
        } catch (NotExistException | CartEmptyException ex) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    /**
     * Delete all the items from cart
     * @param cartItemsDomain the cart items domain
     */
    public void deleteAllItemFromCart(ConcurrentMap<String, CartItemDomain> cartItemsDomain) {
        try {
            cartService.deleteAllItemsFromCart(cartItemsDomain);
        } catch (NotExistException | CartEmptyException ex) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

}
