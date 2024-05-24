package com.ecommerce.shared.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PasswordEncodeConfig {

    private static final String BCRYPT_ENCODE_ID = "bcrypt";
    private static final String PBKDF2_ENCODE_ID = "pbkdf2";
    private static final String SCRIPT_ENCODE_ID = "scrypt";

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(BCRYPT_ENCODE_ID, new BCryptPasswordEncoder());
        encoders.put(PBKDF2_ENCODE_ID, Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put(SCRIPT_ENCODE_ID, SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        return new DelegatingPasswordEncoder(BCRYPT_ENCODE_ID, encoders);
    }
}
