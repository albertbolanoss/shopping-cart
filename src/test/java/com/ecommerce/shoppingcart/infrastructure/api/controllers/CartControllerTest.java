package com.ecommerce.shoppingcart.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecommerce.shoppingcart.application.AddCartItemApp;
import com.ecommerce.shoppingcart.application.CartCheckoutApp;
import com.ecommerce.shoppingcart.application.DeleteCartItemApp;
import com.ecommerce.shoppingcart.application.GetPaymentSummaryApp;
import com.perficient.shoppingcart.application.api.model.CheckoutPayMethodReq;
import com.ecommerce.shoppingcart.domain.model.PaymentMethod;
import com.ecommerce.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.ecommerce.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.ecommerce.shoppingcart.infrastructure.mother.PaymentSummaryDomainMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    private final String PRODUCT_ITEM_URI = "/api/v1/product/%s/item";

    private final String ITEMS_URI = "/api/v1/items";

    private final String CHECKOUT_URI = "/api/v1/checkout";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AddCartItemApp addCartItemService;

    @MockBean
    private DeleteCartItemApp deleteCartItemApp;

    @MockBean
    private GetPaymentSummaryApp getPaymentSummaryApp;

    @MockBean
    private CartCheckoutApp cartCheckoutApp;

    @Test
    void addItemWhenIsSuccessfully() throws Exception {
        var productId = UUID.randomUUID().toString();
        var addItemURI = String.format(PRODUCT_ITEM_URI, productId);
        var cartItemDomain = CartItemDomainMother.random();

        when(addCartItemService.addItem(any(ProductIdDomain.class), any())).thenReturn(cartItemDomain);

        mvc.perform(MockMvcRequestBuilders
                        .post(addItemURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void addItemWhenProductNoFound() throws Exception {
        var productId = UUID.randomUUID().toString();
        var addItemURI = String.format(PRODUCT_ITEM_URI, productId);

        when(addCartItemService.addItem(any(ProductIdDomain.class), any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Product was not found"));

        mvc.perform(MockMvcRequestBuilders
                        .post(addItemURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteItem() throws Exception {
        var productId = UUID.randomUUID().toString();
        var addItemURI = String.format(PRODUCT_ITEM_URI, productId);
        var cartItemDomain = CartItemDomainMother.random();

        when(deleteCartItemApp.deleteItem(any(ProductIdDomain.class), any())).thenReturn(Optional.of(cartItemDomain));

        mvc.perform(MockMvcRequestBuilders
                        .delete(addItemURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    void deleteItemWhenDeleteProduct() throws Exception {
        var productId = UUID.randomUUID().toString();
        var addItemURI = String.format(PRODUCT_ITEM_URI, productId);

        when(deleteCartItemApp.deleteItem(any(ProductIdDomain.class), any())).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders
                        .delete(addItemURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCartItems() throws Exception {
        var paymentSummaryDomain = PaymentSummaryDomainMother.random();
        var cartItemDomain = paymentSummaryDomain.getCartItemDomain().stream().findFirst()
                .orElse(null);

        when(getPaymentSummaryApp.getPaymentSummary(any(PaymentMethod.class), any()))
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

    @Test
    void deleteAllItems() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete(ITEMS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void checkout() throws Exception {

        var addCustomerReq = new CheckoutPayMethodReq().paymentMethodText("CASH");
        var paymentSummaryDomain = PaymentSummaryDomainMother.random();

        when(cartCheckoutApp.checkout(any(PaymentMethod.class), any())).thenReturn(paymentSummaryDomain);

        mvc.perform(MockMvcRequestBuilders
                        .post(CHECKOUT_URI)
                        .content(mapper.writeValueAsString(addCustomerReq))
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
