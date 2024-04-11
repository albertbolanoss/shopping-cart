package com.ecommerce.shared.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Map;

import static com.ecommerce.shared.infrastructure.config.security.SecurityConstant.ENCODER_ID;

@Configuration
public class EncryptConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders =
                Map.of(
                        ENCODER_ID, new BCryptPasswordEncoder(),
                        "pbkdf2", Pbkdf2PasswordEncoder.
                                defaultsForSpringSecurity_v5_8(),
                        "scrypt", SCryptPasswordEncoder
                                .defaultsForSpringSecurity_v5_8());
        return new DelegatingPasswordEncoder
                (ENCODER_ID, encoders);
    }
}
