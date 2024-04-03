package com.perficient.shoppingcart.infrastructure.api.controllers;


import com.perficient.shoppingcart.application.GetCustomersByFiltersApp;
import com.perficient.shoppingcart.application.RegisterCustomerApp;
import com.perficient.shoppingcart.application.api.controller.CustomerApi;
import com.perficient.shoppingcart.application.api.model.AddCustomerReq;
import com.perficient.shoppingcart.application.api.model.GetCustomerPageReq;
import com.perficient.shoppingcart.domain.valueobjects.CustomerReqFilterDomain;
import com.perficient.shoppingcart.infrastructure.api.hateoas.CustomerPageModelAssembler;
import com.perficient.shoppingcart.infrastructure.mappers.CustomerDomainMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;

/**
 * Customer controller
 */
@RestController
@Validated
public class CustomerController implements CustomerApi {
    /**
     * Register customer service
     */
    @Autowired
    private RegisterCustomerApp registerCustomerService;

    /**
     * The get customers filter service
     */
    @Autowired
    private GetCustomersByFiltersApp getCustomersByFiltersService;

    /**
     * Customer model assembler
     */
    @Autowired
    private CustomerPageModelAssembler customerPageModelAssembler;

    /**
     * Register a new customer
     * @param addCustomerReq New Customer (required)
     * @return a response entity
     */
    public ResponseEntity<Void> createCustomer(@Valid AddCustomerReq addCustomerReq) {
        var customer = CustomerDomainMapper.convertFromARequest(addCustomerReq);
        registerCustomerService.register(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<GetCustomerPageReq> getCustomers(Integer offset, Integer limit, String firstName,
                                                           String lastName, String email, List<String> sort) {

        CustomerReqFilterDomain customerDomain = new CustomerReqFilterDomain(
                firstName, lastName, email, offset, limit, sort);

        var customerPageDomain = getCustomersByFiltersService.findByFilter(customerDomain);

        return Optional.ofNullable(customerPageDomain)
                .map(customerPageModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());

    }
}
