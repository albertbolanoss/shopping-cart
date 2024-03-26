package com.perficient.shoppingcart.infrastructure.api.controllers;


import com.perficient.shoppingcart.application.api.controller.CustomerApi;
import com.perficient.shoppingcart.application.api.model.AddCustomerReq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class CustomerController implements CustomerApi {
    public ResponseEntity<Void> createCustomer(@Valid AddCustomerReq addCustomerReq) throws Exception {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
