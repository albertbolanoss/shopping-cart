package com.ecommerce.shoppingcart.application;

import com.ecommerce.shoppingcart.domain.exceptions.CartEmptyException;
import com.ecommerce.shoppingcart.domain.model.Cart;
import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.ProductIdDomain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class DeleteCartItemApp {
    /**
     * Delete an item from cart
     * @param productIdDomain the prodict id domain
     * @param cartItemsDomain the cart items domain
     * @return a optional of cart item
     */
    public Optional<CartItemDomain> deleteItem(ProductIdDomain productIdDomain, List<CartItemDomain> cartItemsDomain) {
        try {
            var cart = new Cart(cartItemsDomain);

            return cart.removeItem(productIdDomain);
        } catch (CartEmptyException ex) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

}
