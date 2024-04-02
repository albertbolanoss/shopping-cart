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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddCartItemServiceTest {
    private AddCartItemService addCartItemService;

    @Mock
    private CartService cartService;

    @BeforeEach
    void init() {
        addCartItemService = new AddCartItemService(cartService);
    }

    @Test
    void addSuccessfully() {
        var productIdDomain = ProductIdDomainMother.random();
        var cartItemDomain = CartItemDomainMother.random();
        var expectedCartItemDomain = CartItemDomainMother.random();

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productIdDomain.getId(), cartItemDomain);

        when(cartService.getItemFromStock(any(), any())).thenReturn(expectedCartItemDomain);

        var actualCartItemDomain = addCartItemService.add(productIdDomain, cartItemsDomain);

        assertNotNull(actualCartItemDomain);
        assertEquals(expectedCartItemDomain.getQuantity(), actualCartItemDomain.getQuantity());
        assertEquals(expectedCartItemDomain.getUnitPrice(), actualCartItemDomain.getUnitPrice());
    }

    @Test
    void addProductNotExist() {
        var productIdDomain = ProductIdDomainMother.random();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        when(cartService.getItemFromStock(any(), any())).thenThrow(new NotExistException("The product not exist"));

        assertThrows(HttpClientErrorException.class, () -> addCartItemService.add(productIdDomain, cartItemsDomain));
    }

    @Test
    void addProductNotAvailable() {
        var productIdDomain = ProductIdDomainMother.random();
        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();

        when(cartService.getItemFromStock(any(), any()))
                .thenThrow(new ProductNotAvailableException("There is not in the stock"));

        assertThrows(HttpClientErrorException.class, () -> addCartItemService.add(productIdDomain, cartItemsDomain));
    }
}
