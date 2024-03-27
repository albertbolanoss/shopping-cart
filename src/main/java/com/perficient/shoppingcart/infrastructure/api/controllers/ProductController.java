package com.perficient.shoppingcart.infrastructure.api.controllers;

import com.perficient.shoppingcart.application.api.controller.ProductApi;
import com.perficient.shoppingcart.application.api.model.AddProductReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ProductController implements ProductApi {
    public ResponseEntity<Void> addProduct(AddProductReq addProductReq) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
