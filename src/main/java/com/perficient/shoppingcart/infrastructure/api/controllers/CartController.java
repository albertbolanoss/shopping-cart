package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.perficient.shoppingcart.application.AddCartItemService;
import com.perficient.shoppingcart.application.DeleteCartItemService;
import com.perficient.shoppingcart.application.api.controller.CartApi;
import com.perficient.shoppingcart.application.api.model.PaymentSummaryReq;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mappers.ItemModelApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
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

        addItemFromStock.addItemToCart(productIdDomain, cartItems);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Get the customer session cart items
     * @return a list api model items
     */
    @Override
    public ResponseEntity<PaymentSummaryReq> getCartItems(String paymentMethod) {
        PaymentSummaryReq paymentSummary = new PaymentSummaryReq()
                .items(ItemModelApiMapper.fromDomain(cartItems))
                .total(new BigDecimal(0));

        return ResponseEntity.ok(paymentSummary);
    }

    /**
     * Delete an item from the cart
     * @param productId Product Identifier (required)
     * @return response entity
     */
    @Override
    public ResponseEntity<Void> deleteItem(String productId) {
        var productIdDomain = new ProductIdDomain(productId);

        deleteCartItemService.deleteItemFromCart(productIdDomain, cartItems);

        return ResponseEntity.noContent().build();
    }


    /**
     * Delete all the item from of the cart
     * @return response entity
     */
    @Override
    public ResponseEntity<Void> deleteAllItems() {
        deleteCartItemService.deleteAllItemFromCart(cartItems);

        return ResponseEntity.noContent().build();
    }

}
