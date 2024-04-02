package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.perficient.shoppingcart.application.AddCartItemService;
import com.perficient.shoppingcart.application.DeleteCartItemService;
import com.perficient.shoppingcart.application.api.controller.CartApi;
import com.perficient.shoppingcart.application.api.model.Item;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mappers.ItemModelApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@Validated
@SessionScope
public class CartController implements CartApi {
    /**
     * Add item from stock service
     */
    private final AddCartItemService addItemFromStock;

    /**
     * Delete cart item service
     */
    private final DeleteCartItemService deleteCartItemService;

    /**
     * The session cart items
     */
    private ConcurrentMap<String, CartItemDomain> cartItems = new ConcurrentHashMap<>();

    @Autowired
    public CartController(AddCartItemService addItemFromStock, DeleteCartItemService deleteCartItemService) {
        this.addItemFromStock = addItemFromStock;
        this.deleteCartItemService = deleteCartItemService;
    }

    /**
     * Add a item to the card
     * @param productId Product Identifier (required)
     * @return a Response entity
     */
    @Override
    public ResponseEntity<Void> addItem(String productId)  {
        var productIdDomain = new ProductIdDomain(productId);
        var chartItemDomain = addItemFromStock.add(productIdDomain, cartItems);

        if (chartItemDomain != null) {
            cartItems.put(productIdDomain.getId(), chartItemDomain);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Get the customer session cart items
     * @return a list api model items
     */
    @Override
    public ResponseEntity<List<Item>> getCartItems() {
        return ResponseEntity.ok(ItemModelApiMapper.fromDomain(cartItems));
    }

    /**
     * Delete an item from the cart
     * @param productId Product Identifier (required)
     * @return response entity
     */
    @Override
    public ResponseEntity<Void> deleteItem(String productId) {
        var productIdDomain = new ProductIdDomain(productId);
        ConcurrentHashMap<String, CartItemDomain> cart = new ConcurrentHashMap<>(cartItems);

        cartItems =  deleteCartItemService.deleteItemFromCart(productIdDomain, cart);

        return ResponseEntity.noContent().build();
    }


    /**
     * Delete all the item from of the cart
     * @return response entity
     */
    @Override
    public ResponseEntity<Void> deleteAllItems() {
        ConcurrentHashMap<String, CartItemDomain> cart = new ConcurrentHashMap<>(cartItems);
        deleteCartItemService.deleteAllItemFromCart(cart);

        cartItems = new ConcurrentHashMap<>();

        return ResponseEntity.noContent().build();
    }

}
