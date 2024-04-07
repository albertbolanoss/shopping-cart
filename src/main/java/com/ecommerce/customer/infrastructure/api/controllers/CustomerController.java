package com.ecommerce.customer.infrastructure.api.controllers;


import com.ecommerce.customer.application.GetCustomersByFiltersApp;
import com.ecommerce.customer.application.RegisterCustomerApp;
import com.ecommerce.customer.domain.valueobjects.CustomerReqFilterDomain;
import com.ecommerce.customer.infrastructure.api.hateoas.CustomerPageModelAssembler;
import com.ecommerce.customer.infrastructure.mappers.CustomerDomainMapper;
import com.perficient.shoppingcart.application.api.controller.UserApi;
import com.perficient.shoppingcart.application.api.model.AddUserReq;
import com.perficient.shoppingcart.application.api.model.GetUsersPageReq;
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
public class CustomerController implements UserApi {
    /**
     * Register customer service
     */
    private final RegisterCustomerApp registerCustomerService;

    /**
     * The get customers filter service
     */
    private final GetCustomersByFiltersApp getCustomersByFiltersService;

    /**
     * Customer model assembler
     */
    private final CustomerPageModelAssembler customerPageModelAssembler;

    @Autowired
    public CustomerController(RegisterCustomerApp registerCustomerService, GetCustomersByFiltersApp getCustomersByFiltersService, CustomerPageModelAssembler customerPageModelAssembler) {
        this.registerCustomerService = registerCustomerService;
        this.getCustomersByFiltersService = getCustomersByFiltersService;
        this.customerPageModelAssembler = customerPageModelAssembler;
    }

    /**
     * Register a new customer
     * @param addCustomerReq New Customer (required)
     * @return a response entity
     */
    @Override
    public ResponseEntity<Void> createUser(@Valid AddUserReq addCustomerReq) {
        var customer = CustomerDomainMapper.convertFromARequest(addCustomerReq);
        registerCustomerService.register(customer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GetUsersPageReq> getUsers(Integer offset, Integer limit, String firstName,
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
