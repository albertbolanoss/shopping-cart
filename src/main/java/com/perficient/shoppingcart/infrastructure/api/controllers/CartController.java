package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.perficient.shoppingcart.application.AddCartItemService;
import com.perficient.shoppingcart.application.api.controller.CartApi;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@Validated
public class CartController implements CartApi {
    /**
     * Add item from stock service
     */
    private final AddCartItemService addItemFromStock;
    /**
     * The session cart items
     */
    private ConcurrentHashMap<String, CartItemDomain> cartItems = new ConcurrentHashMap<>();

    @Autowired
    public CartController(AddCartItemService addItemFromStock) {
        this.addItemFromStock = addItemFromStock;
    }

    /**
     * update
     * @param productId Product Identifier (required)
     * @return
     */
    public ResponseEntity<Void> addItem(String productId)  {
        var productIdDomain = new ProductIdDomain(productId);
        var chartItem = addItemFromStock.add(productIdDomain);

        cartItems.put(productId, chartItem);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
