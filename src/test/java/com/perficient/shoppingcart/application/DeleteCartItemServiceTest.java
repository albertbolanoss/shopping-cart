package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.exceptions.CartEmptyException;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Test
    void deleteItemFromCartEmptyCar() {
        var productIdDomain = ProductIdDomainMother.random();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        when(cartService.deleteItemFromCart(any(ProductIdDomain.class), any()))
                .thenThrow(new CartEmptyException("The cart does not contain any elements"));

        assertThrows(HttpClientErrorException.class,
                () -> deleteCartItemService.deleteItemFromCart(productIdDomain, cartItemsDomain));
    }

    @Test
    void deleteAllItemFromCart() {
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        deleteCartItemService.deleteAllItemFromCart(cartItemsDomain);

        verify(cartService, times(1)).deleteAllItemFromCart(any());
    }

    @Test
    void deleteAllItemFromCartEmptyCar() {
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        doThrow(new CartEmptyException("The cart does not contain any elements"))
                .when(cartService).deleteAllItemFromCart(any());


        assertThrows(HttpClientErrorException.class,
                () -> deleteCartItemService.deleteAllItemFromCart(cartItemsDomain));
    }

    @Test
    void deleteAllItemFromCartNullable() {
        doThrow(new CartEmptyException("The cart does not contain any elements"))
                .when(cartService).deleteAllItemFromCart(any());


        assertThrows(HttpClientErrorException.class,
                () -> deleteCartItemService.deleteAllItemFromCart(null));
    }

}
