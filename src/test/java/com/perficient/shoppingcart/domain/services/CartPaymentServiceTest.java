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

@SpringBootTest
public class CartPaymentServiceTest {
    @Autowired
    private CartPaymentService cartPaymentTotalService;

    @Test
    void calculateTotalWithFeeForVisa() {
        var productDomain = ProductDomainMother.random();
        var cartItemDomain = CartItemDomainMother.withFixValues();
        var expected = new BigDecimal("46204.1674668");

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        var total = cartPaymentTotalService.calculateTotalWithFee(PaymentMethod.VISA, cartItemsDomain);

        assertEquals(expected, total);
    }

    @Test
    void calculateTotalWithFeeForMasterCard() {
        var productDomain = ProductDomainMother.random();
        var cartItemDomain = CartItemDomainMother.withFixValues();
        var expected = new BigDecimal("97221.335647000");

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        var total = cartPaymentTotalService.calculateTotalWithFee(PaymentMethod.MASTERCARD, cartItemsDomain);

        assertEquals(expected, total);
    }

    @Test
    void calculateTotalWithFeeForCash() {
        var productDomain = ProductDomainMother.random();
        var cartItemDomain = CartItemDomainMother.withFixValues();
        var expected = new BigDecimal("111255.38728500");

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productDomain.getProductIdDomain().getId(), cartItemDomain);

        var total = cartPaymentTotalService.calculateTotalWithFee(PaymentMethod.CASH, cartItemsDomain);

        assertEquals(expected, total);
    }
}
