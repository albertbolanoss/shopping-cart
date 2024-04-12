package com.ecommerce.user.infrastructure.api.controllers;

import com.perficient.shoppingcart.application.api.controller.AuthenticationApi;
import com.perficient.shoppingcart.application.api.model.SignInReq;
import com.perficient.shoppingcart.application.api.model.SignedInUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AuthenticationController implements AuthenticationApi {

    @GetMapping("/api/v1/auth/token")
    public CsrfToken getCsrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }

    public ResponseEntity<SignedInUser> signIn(SignInReq signInReq) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
