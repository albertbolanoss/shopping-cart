package com.ecommerce.authentication.infrastructure.api;

import com.ecommerce.shared.infrastructure.config.JWTConfig;
import com.perficient.shoppingcart.application.api.controller.AuthenticationApi;
import com.perficient.shoppingcart.application.api.model.SignInReq;
import com.perficient.shoppingcart.application.api.model.SignedInUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AuthenticationController implements AuthenticationApi {
    private final JWTConfig JWTConfig;

    public AuthenticationController(JWTConfig JWTConfig) {
        this.JWTConfig = JWTConfig;
    }

    public ResponseEntity<SignedInUser> signIn(SignInReq signInReq) {

        String token = JWTConfig.create(
            org.springframework.security.core.userdetails.User.builder()
                .username(signInReq.getUsername())
                .password(signInReq.getPassword())
                .authorities("ADMIN")
                .build()
        );

        return  ok(new SignedInUser()
                .username(signInReq.getUsername())
                .accessToken(token)
                .userId(UUID.randomUUID().toString()));
    }
}
