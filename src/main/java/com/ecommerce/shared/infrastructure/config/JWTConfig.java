package com.ecommerce.shared.infrastructure.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Component
public class JWTConfig {
  private final RSAPrivateKey privateKey;
  private final RSAPublicKey publicKey;

  public static final long EXPIRATION_TIME = 900_000;
  public static final String ROLE_CLAIM = "roles";

  private JWTProperties jwtProperties;

  public JWTConfig(@Lazy RSAPrivateKey privateKey,
                   @Lazy RSAPublicKey publicKey,
                   @Lazy JWTProperties jwtProperties) {
    this.privateKey = privateKey;
    this.publicKey = publicKey;
    this.jwtProperties = jwtProperties;
  }

  @Bean
  public JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
    return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
  }

  public String create(UserDetails principal) {
    final long now = System.currentTimeMillis();
    ;
    return JWT.create()
        .withIssuer(jwtProperties.getIssue())
        .withSubject(principal.getUsername())
        .withClaim(
            jwtProperties.getClaim(),
            principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                    .toList())
        .withIssuedAt(new Date(now))
        .withExpiresAt(new Date(now + jwtProperties.getExpiration()))
        .sign(Algorithm.RSA256(publicKey, privateKey));
  }
}
