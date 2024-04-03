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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

        doNothing().when(cartService).deleteItemFromCart(any(ProductIdDomain.class), any());

        deleteCartItemService.deleteItemFromCart(productIdDomain, cartItemsDomain);

        verify(cartService, atLeastOnce()).deleteItemFromCart(any(ProductIdDomain.class), any());
    }

    @Test
    void deleteItemFromCartNoExistProduct() {
        var productIdDomain = ProductIdDomainMother.random();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        doThrow(new NotExistException("the product not exist")).when(cartService)
                .deleteItemFromCart(any(ProductIdDomain.class), any());

        assertThrows(HttpClientErrorException.class,
                () -> deleteCartItemService.deleteItemFromCart(productIdDomain, cartItemsDomain));
    }

    @Test
    void deleteItemFromCartEmptyCar() {
        var productIdDomain = ProductIdDomainMother.random();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        doThrow(new CartEmptyException("The cart does not contain any elements")).when(cartService)
                .deleteItemFromCart(any(ProductIdDomain.class), any());


        assertThrows(HttpClientErrorException.class,
                () -> deleteCartItemService.deleteItemFromCart(productIdDomain, cartItemsDomain));
    }

    @Test
    void deleteAllItemFromCart() {
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        deleteCartItemService.deleteAllItemFromCart(cartItemsDomain);

        verify(cartService, times(1)).deleteAllItemsFromCart(any());
    }

    @Test
    void deleteAllItemFromCartEmptyCar() {
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        doThrow(new CartEmptyException("The cart does not contain any elements"))
                .when(cartService).deleteAllItemsFromCart(any());


        assertThrows(HttpClientErrorException.class,
                () -> deleteCartItemService.deleteAllItemFromCart(cartItemsDomain));
    }

    @Test
    void deleteAllItemFromCartNullable() {
        doThrow(new CartEmptyException("The cart does not contain any elements"))
                .when(cartService).deleteAllItemsFromCart(any());


        assertThrows(HttpClientErrorException.class,
                () -> deleteCartItemService.deleteAllItemFromCart(null));
    }

}
