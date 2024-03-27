package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.perficient.shoppingcart.application.api.controller.CartApi;
import com.perficient.shoppingcart.application.api.model.AddCustomerReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CartController implements CartApi {
    public ResponseEntity<Void> createCart(AddCustomerReq addCustomerReq)  {
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
