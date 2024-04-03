package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.enumerators.PaymentMethod;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductDomainMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CartPaymentServiceTest {
    @Autowired
    private CartPaymentService cartPaymentTotalService;

    @Test
    void calculateTotalWithFeeForVisa() {
        var productDomain = ProductDomainMother.random();
        var cartItemDomain = CartItemDomainMother.withFixValues();
        var expected = new BigDecimal("46204.1674668");

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        var actual = cartPaymentTotalService.calculateTotalWithFee(PaymentMethod.VISA, cartItemsDomain);

        assertEquals(expected, actual.getTotal());
        assertFalse(actual.getCartItemDomainList().isEmpty());
    }

    @Test
    void calculateTotalWithFeeForVisaWithEmptyCart() {
        var expected = new BigDecimal("0");

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        var actual = cartPaymentTotalService.calculateTotalWithFee(PaymentMethod.VISA, cartItemsDomain);

        assertEquals(expected, actual.getTotal());
        assertTrue(actual.getCartItemDomainList().isEmpty());
    }

    @Test
    void calculateTotalWithFeeForMasterCard() {
        var productDomain = ProductDomainMother.random();
        var cartItemDomain = CartItemDomainMother.withFixValues();
        var expected = new BigDecimal("97221.335647000");

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        var actual = cartPaymentTotalService.calculateTotalWithFee(PaymentMethod.MASTERCARD, cartItemsDomain);

        assertEquals(expected, actual.getTotal());
        assertFalse(actual.getCartItemDomainList().isEmpty());
    }

    @Test
    void calculateTotalWithFeeForCash() {
        var productDomain = ProductDomainMother.random();
        var cartItemDomain = CartItemDomainMother.withFixValues();
        var expected = new BigDecimal("111255.38728500");

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        var actual = cartPaymentTotalService.calculateTotalWithFee(PaymentMethod.CASH, cartItemsDomain);

        assertEquals(expected, actual.getTotal());
        assertFalse(actual.getCartItemDomainList().isEmpty());
    }
}
