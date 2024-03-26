package com.perficient.shoppingcart.infrastructure.api.controllers;


import com.perficient.shoppingcart.application.RegisterCustomerService;
import com.perficient.shoppingcart.application.api.controller.CustomerApi;
import com.perficient.shoppingcart.application.api.model.AddCustomerReq;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * Customer controller
 */
@RestController
@Validated
public class CustomerController implements CustomerApi {
    /**
     * Register customer service
     */
    private final RegisterCustomerService registerCustomerService;

    /**
     * Constructor
     * @param registerCustomerService the Register customer service
     */
    @Autowired
    public CustomerController(RegisterCustomerService registerCustomerService) {
        this.registerCustomerService = registerCustomerService;
    }

    /**
     * Register a new customer
     * @param addCustomerReq New Customer (required)
     * @return a response entity
     */
    public ResponseEntity<Void> createCustomer(@Valid AddCustomerReq addCustomerReq) {
        registerCustomerService.register(addCustomerReq);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
