package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.exceptions.NotExistException;
import com.perficient.shoppingcart.domain.services.CartService;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mother.ProductIdDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class DeleteCartItemServiceTest {
    private DeleteCartItemService deleteCartItemService;
    @Mock
    private CartService cartService;

    @BeforeEach
    void init() {
        deleteCartItemService = new DeleteCartItemService(cartService);
    }

    @Test
    void deleteItemFromCart() {
        var productIdDomain = ProductIdDomainMother.random();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        when(cartService.deleteItemFromCart(any(ProductIdDomain.class), any()))
                .thenReturn(cartItemsDomain);

        var actualCart = deleteCartItemService.deleteItemFromCart(productIdDomain, cartItemsDomain);

        assertNotNull(actualCart);
    }

    @Test
    void deleteItemFromCartNoExistProduct() {
        var productIdDomain = ProductIdDomainMother.random();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        when(cartService.deleteItemFromCart(any(ProductIdDomain.class), any()))
                .thenThrow(new NotExistException("the product not exist"));

        assertThrows(HttpClientErrorException.class,
                () -> deleteCartItemService.deleteItemFromCart(productIdDomain, cartItemsDomain));
    }

}
