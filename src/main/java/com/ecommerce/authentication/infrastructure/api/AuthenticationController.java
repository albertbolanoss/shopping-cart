package com.ecommerce.authentication.infrastructure.api;

import com.ecommerce.shared.infrastructure.config.Authority;
import com.ecommerce.shared.infrastructure.config.JWTConfig;
import com.perficient.shoppingcart.application.api.controller.AuthenticationApi;
import com.perficient.shoppingcart.application.api.model.SignInReq;
import com.perficient.shoppingcart.application.api.model.SignedInUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AuthenticationController implements AuthenticationApi {
    private final JWTConfig JWTConfig;

    public AuthenticationController(JWTConfig JWTConfig) {
        this.JWTConfig = JWTConfig;
    }


    @GetMapping("api/v1/auth/token/csrf")
    public ResponseEntity<CsrfToken> getCsrfToken(org.springframework.security.web.csrf.CsrfToken csrfToken) {
        return ok(csrfToken);
    }

    public ResponseEntity<SignedInUser> signIn(SignInReq signInReq) {

        String token = JWTConfig.create(
            org.springframework.security.core.userdetails.User.builder()
                .username(signInReq.getUsername())
                .password(signInReq.getPassword())
                .authorities(Authority.CART.name(), Authority.CART_READ.name(), Authority.CART_WRITE.name())
                .build()
        );

        return  ok(new SignedInUser()
                .username(signInReq.getUsername())
                .accessToken(token)
                .userId(UUID.randomUUID().toString()));
    }
}
