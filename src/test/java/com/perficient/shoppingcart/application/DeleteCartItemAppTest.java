package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductIdDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteCartItemAppTest {
    private DeleteCartItemApp deleteCartItemApp;

    @BeforeEach
    void init() {
        deleteCartItemApp = new DeleteCartItemApp();
    }

    @Test
    void deleteItemWhenAlreadyExistAnItemOfTheProduct() {
        var cartItemDomain = CartItemDomainMother.twoOrMoreInStock();
        var productIdDomain = cartItemDomain.getProductIdDomain();
        var cartItemsDomain = List.of(CartItemDomainMother.random(), CartItemDomainMother.random(), cartItemDomain);
        var expectedItemsQuantity = cartItemDomain.getQuantity() - 1;

        var actualCartItemDomain = deleteCartItemApp.deleteItem(productIdDomain, cartItemsDomain)
                .orElse(null);

        assertNotNull(actualCartItemDomain);
        assertEquals(expectedItemsQuantity, actualCartItemDomain.getQuantity());
        assertEquals(cartItemDomain.getUnitPrice(), actualCartItemDomain.getUnitPrice());
        assertEquals(cartItemDomain.getProductIdDomain().getId(), actualCartItemDomain.getProductIdDomain().getId());

    }

    @Test
    void deleteItemWhenExistJustOneItemOfTheProduct() {
        var cartItemDomain = CartItemDomainMother.onlyOneInStocks();
        var productIdDomain = cartItemDomain.getProductIdDomain();
        var cartItemsDomain = List.of(CartItemDomainMother.random(), CartItemDomainMother.random(), cartItemDomain);

        var actualCartItemDomain = deleteCartItemApp.deleteItem(productIdDomain, cartItemsDomain)
                .orElse(null);

        assertNull(actualCartItemDomain);

    }

    @Test
    void deleteItemWhenProductIsNotInTheCart() {
        var productIdDomain = ProductIdDomainMother.random();
        var cartItemsDomain = List.of(
                CartItemDomainMother.random(), CartItemDomainMother.random(), CartItemDomainMother.random());

        assertThrows(HttpClientErrorException.class,
                () -> deleteCartItemApp.deleteItem(productIdDomain, cartItemsDomain));
    }

}
