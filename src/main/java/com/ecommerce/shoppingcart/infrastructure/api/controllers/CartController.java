package com.ecommerce.shoppingcart.infrastructure.api.controllers;

import com.ecommerce.shoppingcart.application.AddCartItemApp;
import com.ecommerce.shoppingcart.application.CartCheckoutApp;
import com.ecommerce.shoppingcart.application.DeleteCartItemApp;
import com.ecommerce.shoppingcart.application.GetPaymentSummaryApp;
import com.ecommerce.shoppingcart.domain.model.PaymentMethod;
import com.ecommerce.shoppingcart.infrastructure.mappers.PaymentSummaryReqMapper;
import com.perficient.shoppingcart.application.api.controller.CartApi;
import com.perficient.shoppingcart.application.api.model.CheckoutPayMethodReq;
import com.perficient.shoppingcart.application.api.model.PaymentSummaryReq;
import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.ProductIdDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@Validated
@SessionScope
public class CartController implements CartApi {
    /**
     * Add item from stock service
     */
    private final AddCartItemApp addCartItemApp;

    /**
     * Delete cart item service
     */
    private final DeleteCartItemApp deleteCartItemApp;

    /**
     * Get payment cart summary service
     */
    private final GetPaymentSummaryApp getPaymentSummaryApp;

    /**
     * Cart items checkout app
     */
    private final CartCheckoutApp cartCheckoutApp;

    /**
     * The session cart items
     */
    private ConcurrentMap<String, CartItemDomain> cartItems = new ConcurrentHashMap<>();

    @Autowired
    public CartController(AddCartItemApp addItemFromStock, DeleteCartItemApp deleteCartItemApp,
                          GetPaymentSummaryApp getPaymentSummaryApp, CartCheckoutApp cartCheckoutApp) {
        this.addCartItemApp = addItemFromStock;
        this.deleteCartItemApp = deleteCartItemApp;
        this.getPaymentSummaryApp = getPaymentSummaryApp;
        this.cartCheckoutApp = cartCheckoutApp;
    }

    /**
     * Add a item to the card
     * @param productId Product Identifier (required)
     * @return a Response entity
     */
    @Override
    public ResponseEntity<Void> addItem(String productId)  {
        var id = Optional.ofNullable(productId).map(String::trim).orElse(null);
        var productIdDomain = new ProductIdDomain(id);
        var cartItemsDomain = cartItems.values().stream().toList();
        var cartItemDomain = addCartItemApp.addItem(productIdDomain, cartItemsDomain);

        cartItems.put(id, cartItemDomain);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Get the customer session cart items
     * @return a list api model items
     */
    @Override
    public ResponseEntity<PaymentSummaryReq> getCartItems(String paymentMethodText) {
        var paymentMethod = getPaymentMethodFromText(paymentMethodText);
        var cartItemsDomain = cartItems.values().stream().toList();
        var paymentSummaryReq = PaymentSummaryReqMapper.fromDomain(
                getPaymentSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain));

        return ResponseEntity.ok(paymentSummaryReq);
    }

    /**
     * Delete an item from the cart
     * @param productId Product Identifier (required)
     * @return response entity
     */
    @Override
    public ResponseEntity<Void> deleteItem(String productId) {
        var id = Optional.ofNullable(productId).map(String::trim).orElse(null);
        var productIdDomain = new ProductIdDomain(id);
        var cartItemsDomain = cartItems.values().stream().toList();

        deleteCartItemApp.deleteItem(productIdDomain, cartItemsDomain)
                .ifPresentOrElse(
                        item -> cartItems.put(id, item),
                        () -> cartItems.remove(id)
                );

        return ResponseEntity.noContent().build();
    }


    /**
     * Delete all the item from of the cart
     * @return response entity
     */
    @Override
    public ResponseEntity<Void> deleteAllItems() {
        cartItems.clear();

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PaymentSummaryReq> checkout(CheckoutPayMethodReq checkoutPayMethodReq) {
        var paymentMethod = getPaymentMethodFromText(checkoutPayMethodReq.getPaymentMethodText());
        var cartItemsDomain = cartItems.values().stream().toList();

        var paymentSummary =  PaymentSummaryReqMapper.fromDomain(
                cartCheckoutApp.checkout(paymentMethod, cartItemsDomain));
        cartItems.clear();

        return ResponseEntity.ok(paymentSummary);
    }

    @GetMapping("/api/v1/test")
    public String getTest() {
        return "Test";
    }

    @GetMapping("/api/v1/test2")
    public String getTest2() {
        return "Test2";
    }

    /**
     * Get the payment method from text
     * @param paymentMethodText the payment method in text format
     * @return a Pay Method enum
     */
    private PaymentMethod getPaymentMethodFromText(String paymentMethodText) {
        return Arrays.stream(PaymentMethod.values())
                .filter(value -> value.name().equalsIgnoreCase(paymentMethodText))
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.BAD_REQUEST, "The payment method is no supported yet"));
    }

}
