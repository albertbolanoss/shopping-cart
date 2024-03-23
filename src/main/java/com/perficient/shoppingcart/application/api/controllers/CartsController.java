package com.perficient.shoppingcart.application.api.controllers;

import com.perficient.shoppingcart.application.api.controller.ApiUtil;
import com.perficient.shoppingcart.application.api.controller.CartApi;
import com.perficient.shoppingcart.application.api.model.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class CartsController implements CartApi {
    @Override
    public ResponseEntity<List<Cart>> getCartByCustomerId(String customerId) throws Exception {
        return ok(Collections.EMPTY_LIST);
    }
}
