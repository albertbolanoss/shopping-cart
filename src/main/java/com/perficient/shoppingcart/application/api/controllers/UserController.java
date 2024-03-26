package com.perficient.shoppingcart.application.api.controllers;


import com.perficient.shoppingcart.application.api.controller.UserApi;
import com.perficient.shoppingcart.application.api.model.AddUserReq;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UserController implements UserApi {
    public ResponseEntity<Void> createUser(@Valid AddUserReq addUserReq) throws Exception {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
