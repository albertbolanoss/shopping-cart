package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.shoppingcart.application.AddCartItemService;
import com.perficient.shoppingcart.application.DeleteCartItemService;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mother.CartItemDomainMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest {
    private final String URI = "/api/v1/product/%s/item";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AddCartItemService addCartItemService;

    @MockBean
    private DeleteCartItemService deleteCartItemService;

    @Test
    void addItemToCartSuccessfully() throws Exception {
        var productId = UUID.randomUUID().toString();
        var cartItemDomain = CartItemDomainMother.random();
        var addItemURI = String.format(URI, productId);

        when(addCartItemService.add(any(ProductIdDomain.class), any()))
                .thenReturn(cartItemDomain);

        mvc.perform(MockMvcRequestBuilders
                        .post(addItemURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void addItemToCartGetNullable() throws Exception {
        var productId = UUID.randomUUID().toString();
        var addItemURI = String.format(URI, productId);

        when(addCartItemService.add(any(ProductIdDomain.class), any()))
                .thenReturn(null);

        mvc.perform(MockMvcRequestBuilders
                        .post(addItemURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteItem() throws Exception {
        var productId = UUID.randomUUID().toString();
        var addItemURI = String.format(URI, productId);
        ConcurrentMap<String, CartItemDomain> cartItems = new ConcurrentHashMap<>();

        when(deleteCartItemService.deleteItemFromCart(any(ProductIdDomain.class), any()))
                .thenReturn(cartItems);

        mvc.perform(MockMvcRequestBuilders
                        .delete(addItemURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllItems() throws Exception {
        ConcurrentMap<String, CartItemDomain> cartItems = new ConcurrentHashMap<>();

        doNothing().when(deleteCartItemService).deleteAllItemFromCart(any());

        mvc.perform(MockMvcRequestBuilders
                        .delete(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
