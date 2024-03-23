package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.perficient.shoppingcart.infrastructure.api.controller.CartApi;
import com.perficient.shoppingcart.infrastructure.api.model.Item;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartsController implements CartApi {
    public ResponseEntity<List<Item>> addCartItemsByCustomerId(
            @Parameter(name = "customerId", description = "Customer Identifier", required = true)
            @PathVariable("customerId") String customerId,
            @Parameter(name = "Item", description = "Item object")
            @Valid @RequestBody(required = false) Item item
    ) throws Exception {
        return null;
    }
}
