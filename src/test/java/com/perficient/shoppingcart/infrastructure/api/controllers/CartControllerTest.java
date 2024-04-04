package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perficient.shoppingcart.application.AddCartItemApp;
import com.perficient.shoppingcart.application.DeleteCartItemApp;
import com.perficient.shoppingcart.application.GetPaymentCartSummaryApp;
import com.perficient.shoppingcart.domain.enumerators.PaymentMethod;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mother.PaymentSummaryDomainMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    private final String PRODUCT_ITEM_URI = "/api/v1/product/%s/item";

    private final String ITEMS_URI = "/api/v1/items";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AddCartItemApp addCartItemService;

    @MockBean
    private DeleteCartItemApp deleteCartItemApp;

    @MockBean
    private GetPaymentCartSummaryApp getPaymentCartSummaryApp;

    @Test
    void addItemToCartSuccessfully() throws Exception {
        var productId = UUID.randomUUID().toString();
        var addItemURI = String.format(PRODUCT_ITEM_URI, productId);

        doNothing().when(addCartItemService).addItemToCart(any(ProductIdDomain.class), any());

        mvc.perform(MockMvcRequestBuilders
                        .post(addItemURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteItem() throws Exception {
        var productId = UUID.randomUUID().toString();
        var addItemURI = String.format(PRODUCT_ITEM_URI, productId);

        doNothing().when(deleteCartItemApp).deleteItemFromCart(any(ProductIdDomain.class), any());

        mvc.perform(MockMvcRequestBuilders
                        .delete(addItemURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllItems() throws Exception {
        doNothing().when(deleteCartItemApp).deleteAllItemFromCart(any());

        mvc.perform(MockMvcRequestBuilders
                        .delete(ITEMS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCartItems() throws Exception {
        var paymentSummaryDomain = PaymentSummaryDomainMother.random();
        var cartItemDomain = paymentSummaryDomain.getCartItemDomainList().stream().findFirst()
                .orElse(null);

        when(getPaymentCartSummaryApp.getPaymentSummary(any(PaymentMethod.class), any()))
                .thenReturn(paymentSummaryDomain);

        assert cartItemDomain != null;
        mvc.perform(MockMvcRequestBuilders
                        .get(ITEMS_URI)
                        .queryParam("paymentMethodText", "VISA")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total").value(paymentSummaryDomain.getTotal()))
                .andExpect(jsonPath("$.items[*].id").exists())
                .andExpect(jsonPath("$.items[*].quantity").exists())
                .andExpect(jsonPath("$.items[*].unitPrice").exists());
    }
}
