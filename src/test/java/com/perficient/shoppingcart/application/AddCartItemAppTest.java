package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.exceptions.NotExistException;
import com.perficient.shoppingcart.domain.exceptions.ProductNotAvailableException;
import com.perficient.shoppingcart.domain.services.CartService;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.infrastructure.mother.CartItemDomainMother;
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
import static org.mockito.Mockito.verify;

@SpringBootTest
class AddCartItemAppTest {
    private AddCartItemApp addCartItemService;

    @Mock
    private CartService cartService;

    @BeforeEach
    void init() {
        addCartItemService = new AddCartItemApp(cartService);
    }

    @Test
    void addSuccessfully() {
        var productIdDomain = ProductIdDomainMother.random();
        var cartItemDomain = CartItemDomainMother.random();

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productIdDomain.getId(), cartItemDomain);

        doNothing().when(cartService).addItemToCart(any(), any());

        addCartItemService.addItemToCart(productIdDomain, cartItemsDomain);

        verify(cartService, atLeastOnce()).addItemToCart(any(), any());
    }

    @Test
    void addProductNotExist() {
        var productIdDomain = ProductIdDomainMother.random();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        doThrow(new NotExistException("The product not exist")).when(cartService).addItemToCart(any(), any());

        assertThrows(HttpClientErrorException.class, () -> addCartItemService.addItemToCart(productIdDomain, cartItemsDomain));
    }

    @Test
    void addProductNotAvailable() {
        var productIdDomain = ProductIdDomainMother.random();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        doThrow(new ProductNotAvailableException("There is not in the stock")).when(cartService).addItemToCart(any(), any());

        assertThrows(HttpClientErrorException.class, () -> addCartItemService.addItemToCart(productIdDomain, cartItemsDomain));
    }
}
