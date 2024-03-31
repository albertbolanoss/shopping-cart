package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest {
    private final String URI = "/api/v1/customer/%s/product/%s/item";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;


    void addItemToCartSuccessfully() throws Exception {
        var customerId = UUID.randomUUID().toString();
        var productId = UUID.randomUUID().toString();
        var addItemURI = String.format(URI, customerId, productId);

        mvc.perform(MockMvcRequestBuilders
                        .post(addItemURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
