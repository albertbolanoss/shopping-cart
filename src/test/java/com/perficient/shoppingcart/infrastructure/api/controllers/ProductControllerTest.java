package com.perficient.shoppingcart.infrastructure.api.controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    private final String URI = "/api/v1/product";
}
