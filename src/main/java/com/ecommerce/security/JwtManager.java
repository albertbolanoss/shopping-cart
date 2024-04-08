package com.ecommerce.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import static com.ecommerce.security.Constants.EXPIRATION_TIME;
import static com.ecommerce.security.Constants.ROLE_CLAIM;
import static java.util.stream.Collectors.toList;

@Component
public class JwtManager {

  private final RSAPrivateKey privateKey;
  private final RSAPublicKey publicKey;

  public JwtManager(@Lazy RSAPrivateKey privateKey, @Lazy RSAPublicKey publicKey) {
    this.privateKey = privateKey;
    this.publicKey = publicKey;
  }

  public String create(UserDetails principal) {
    final long now = System.currentTimeMillis();
    return JWT.create()
        .withIssuer("Modern API Development with Spring and Spring Boot")
        .withSubject(principal.getUsername())
        .withClaim(
            ROLE_CLAIM,
            principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList()))
        .withIssuedAt(new Date(now))
        .withExpiresAt(new Date(now + EXPIRATION_TIME))
        .sign(Algorithm.RSA256(publicKey, privateKey));
  }
}
