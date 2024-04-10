package com.ecommerce.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

import static com.ecommerce.security.Constants.AUTHORITY_PREFIX;
import static com.ecommerce.security.Constants.REFRESH_URL;
import static com.ecommerce.security.Constants.ROLE_CLAIM;
import static com.ecommerce.security.Constants.SIGNUP_URL;
import static com.ecommerce.security.Constants.TOKEN_URL;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
  @Value("${app.security.jwt.keystore-location}")
  private String keyStorePath;
  @Value("${app.security.jwt.keystore-password}")
  private String keyStorePassword;
  @Value("${app.security.jwt.key-alias}")
  private String keyAlias;
  @Value("${app.security.jwt.private-key-passphrase}")
  private String privateKeyPassphrase;

  @Bean
  public KeyStore keyStore() {
    try {
      KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      InputStream resStream = Thread.currentThread().
              getContextClassLoader().getResourceAsStream(keyStorePath);
      keyStore.load(resStream, keyStorePassword.toCharArray());

      return keyStore;
    } catch (IOException | CertificateException |
             NoSuchAlgorithmException | KeyStoreException e) {
      log.error("Unable to load keystore: {}", keyStorePath, e);
    }

    throw new IllegalArgumentException("Can't load keystore");
  }

  @Bean
  public RSAPrivateKey jwtSigningKey(KeyStore keyStore) {
    try {
      Key key = keyStore.getKey(keyAlias, privateKeyPassphrase.toCharArray());
      if (key instanceof RSAPrivateKey) {
        return (RSAPrivateKey) key;
      }
    } catch (UnrecoverableKeyException |
             NoSuchAlgorithmException | KeyStoreException e) {
      log.error("key from keystore: {}", keyStorePath, e);
    }
    throw new IllegalArgumentException("Cant load private key");
  }

  @Bean
  public RSAPublicKey jwtValidationKey(KeyStore keyStore) {
    try {
      Certificate certificate = keyStore.getCertificate(keyAlias);
      PublicKey publicKey = certificate.getPublicKey();
      if (publicKey instanceof RSAPublicKey) {
        return (RSAPublicKey) publicKey;
      }
    } catch (KeyStoreException e) {
      log.error("key from keystore: {}", keyStorePath, e);
    }
    throw new IllegalArgumentException("Can't load public key");
  }

  @Bean
  public JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
    return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
    configuration.addAllowedOrigin("*");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    UrlBasedCorsConfigurationSource source = new
            UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }


  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(req ->
        req.requestMatchers(toH2Console()).permitAll()
          .requestMatchers(new AntPathRequestMatcher(
                        TOKEN_URL, HttpMethod.GET.name())).permitAll()
          .requestMatchers(new AntPathRequestMatcher(
                  TOKEN_URL, HttpMethod.POST.name())).permitAll()
          .requestMatchers(new AntPathRequestMatcher(
                  TOKEN_URL, HttpMethod.DELETE.name())).permitAll()
          .requestMatchers(new AntPathRequestMatcher(
                  SIGNUP_URL, HttpMethod.POST.name())).permitAll()
          .requestMatchers(new AntPathRequestMatcher(
                  REFRESH_URL, HttpMethod.POST.name())).permitAll()
          .requestMatchers("/api/v1/user/**")
          .hasAuthority(RoleEnum.ADMIN.getAuthority())
          .anyRequest().authenticated()
      )
      .csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository())
        .ignoringRequestMatchers(toH2Console())
      )
      .oauth2ResourceServer(oauth2ResourceServer ->
              oauth2ResourceServer.jwt(jwt ->
                      jwt.jwtAuthenticationConverter(getJwtAuthenticationConverter())))
      .sessionManagement(sessionManagement ->
              sessionManagement
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
  }

  private Converter<Jwt, AbstractAuthenticationToken>
  getJwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter authorityConverter =
            new JwtGrantedAuthoritiesConverter();
    authorityConverter.setAuthorityPrefix(AUTHORITY_PREFIX);
    authorityConverter.setAuthoritiesClaimName(ROLE_CLAIM);
    JwtAuthenticationConverter converter =
            new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(authorityConverter);
    return converter;
  }

}
