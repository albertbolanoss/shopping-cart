package com.ecommerce.shared.infrastructure.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    public static final String ROLE_CLAIM = "roles";
    public static final String AUTHORITY_PREFIX = "ROLE_";



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/api/v1/auth/token").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll();
                    req.requestMatchers(HttpMethod.GET, "/api/v1/auth/token/csrf").permitAll();

                    req.requestMatchers(HttpMethod.POST, "/api/v1/product/*/item")
                            .hasAnyRole(Authority.getWriteAuthorities(Authority.CART));
                    req.requestMatchers(HttpMethod.DELETE, "/api/v1/product/*/item")
                            .hasAnyRole(Authority.getWriteAuthorities(Authority.CART));
                    req.requestMatchers(HttpMethod.DELETE, "/api/v1/items")
                            .hasAnyRole(Authority.getWriteAuthorities(Authority.CART));

                    req.requestMatchers(HttpMethod.GET, "/api/v1/items")
                            .hasAnyRole(Authority.getReadAuthorities(Authority.CART));

                    req.requestMatchers(HttpMethod.POST, "/api/v1/checkout")
                            .hasAnyRole(Authority.getReadAuthorities(Authority.CART));

                    req.requestMatchers(HttpMethod.POST, "/api/v1/user")
                            .hasAnyRole(Authority.getReadAuthorities(Authority.ADMIN));

                    req.anyRequest().denyAll();
                })
                .csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                        .ignoringRequestMatchers(toH2Console())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(getJwtAuthenticationConverter()))
                )
                .build();
    }

    private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        var authorityConverter = new JwtGrantedAuthoritiesConverter();
        authorityConverter.setAuthorityPrefix(AUTHORITY_PREFIX);
        authorityConverter.setAuthoritiesClaimName(ROLE_CLAIM);

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authorityConverter);

        return converter;
    }

}
