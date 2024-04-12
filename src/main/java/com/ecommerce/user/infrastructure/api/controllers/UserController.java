package com.ecommerce.user.infrastructure.api.controllers;


import com.ecommerce.user.application.GetUsersByFiltersApp;
import com.ecommerce.user.application.RegisterUserApp;
import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import com.ecommerce.user.infrastructure.api.hateoas.UserPageModelAssembler;
import com.ecommerce.user.infrastructure.mappers.NewUserDomainMapper;
import com.perficient.shoppingcart.application.api.controller.UserApi;
import com.perficient.shoppingcart.application.api.model.AddUserReq;
import com.perficient.shoppingcart.application.api.model.GetUsersPageReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserController implements UserApi {
    /**
     * Register customer service
     */
    private final RegisterUserApp registerCustomerService;

    /**
     * The get customers filter service
     */
    private final GetUsersByFiltersApp getCustomersByFiltersService;

    /**
     * Customer model assembler
     */
    private final UserPageModelAssembler userPageModelAssembler;

    /**
     * Password encoder
     */
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(RegisterUserApp registerCustomerService,
                          GetUsersByFiltersApp getCustomersByFiltersService,
                          UserPageModelAssembler userPageModelAssembler,
                          PasswordEncoder passwordEncoder) {
        this.registerCustomerService = registerCustomerService;
        this.getCustomersByFiltersService = getCustomersByFiltersService;
        this.userPageModelAssembler = userPageModelAssembler;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a new customer
     * @param addCustomerReq New Customer (required)
     * @return a response entity
     */
    @Override
    public ResponseEntity<Void> createUser(AddUserReq addCustomerReq) {
        var encodePassword = passwordEncoder.encode(addCustomerReq.getPassword());
        var userDomain = NewUserDomainMapper.convertFromAddUserReq(addCustomerReq.password(encodePassword));
        registerCustomerService.register(userDomain);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GetUsersPageReq> getUsers(Integer offset, Integer limit,
                                                    String firstName, String lastName,
                                                    String email, List<String> sort) {

        UserReqFilterDomain customerDomain = new UserReqFilterDomain(
                firstName, lastName, email, offset, limit, sort);

        var customerPageDomain = getCustomersByFiltersService.findByFilter(customerDomain);

        return Optional.ofNullable(customerPageDomain)
                .map(userPageModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());

    }
}
