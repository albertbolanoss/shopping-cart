package com.ecommerce.shared.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.security.jwt")
@Getter
@Setter
public class JWTProperties {
    private String issue;
    private Long expiration;
    private String claim;
}
