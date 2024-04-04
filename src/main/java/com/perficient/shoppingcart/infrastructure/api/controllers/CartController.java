package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.perficient.shoppingcart.application.AddCartItemApp;
import com.perficient.shoppingcart.application.DeleteCartItemApp;
import com.perficient.shoppingcart.application.GetPaymentCartSummaryApp;
import com.perficient.shoppingcart.application.api.controller.CartApi;
import com.perficient.shoppingcart.application.api.model.PaymentSummaryReq;
import com.perficient.shoppingcart.domain.enumerators.PaymentMethod;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mappers.PaymentSummaryReqMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@Validated
@SessionScope
public class CartController implements CartApi {
    /**
     * Add item from stock service
     */
    private final AddCartItemApp addItemFromStock;

    /**
     * Delete cart item service
     */
    private final DeleteCartItemApp deleteCartItemApp;

    /**
     * Get payment cart summary service
     */
    private final GetPaymentCartSummaryApp getPaymentCartSummaryApp;

    /**
     * The session cart items
     */
    private ConcurrentMap<String, CartItemDomain> cartItems = new ConcurrentHashMap<>();

    @Autowired
    public CartController(AddCartItemApp addItemFromStock, DeleteCartItemApp deleteCartItemApp,
                          GetPaymentCartSummaryApp getPaymentCartSummaryApp) {
        this.addItemFromStock = addItemFromStock;
        this.deleteCartItemApp = deleteCartItemApp;
        this.getPaymentCartSummaryApp = getPaymentCartSummaryApp;
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
    public ResponseEntity<PaymentSummaryReq> getCartItems(String paymentMethodText) {

        var paymentMethod = Arrays.stream(PaymentMethod.values())
                .filter(value -> value.name().equals(paymentMethodText))
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.BAD_REQUEST, "The payment method is no supported yet"));

        var paymentSummaryReq = PaymentSummaryReqMapper.fromDomain(
                getPaymentCartSummaryApp.getPaymentSummary(paymentMethod, cartItems));

        return ResponseEntity.ok(paymentSummaryReq);
    }

    /**
     * Delete an item from the cart
     * @param productId Product Identifier (required)
     * @return response entity
     */
    @Override
    public ResponseEntity<Void> deleteItem(String productId) {
        var productIdDomain = new ProductIdDomain(productId);

        deleteCartItemApp.deleteItemFromCart(productIdDomain, cartItems);

        return ResponseEntity.noContent().build();
    }


    /**
     * Delete all the item from of the cart
     * @return response entity
     */
    @Override
    public ResponseEntity<Void> deleteAllItems() {
        deleteCartItemApp.deleteAllItemFromCart(cartItems);

        return ResponseEntity.noContent().build();
    }

}
