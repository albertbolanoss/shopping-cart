package com.ecommerce.shared.infrastructure.config.security;

import lombok.extern.slf4j.Slf4j;
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

import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
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
                  Constant.TOKEN_URL, HttpMethod.GET.name())).permitAll()
          .requestMatchers(new AntPathRequestMatcher(
                  Constant.TOKEN_URL, HttpMethod.POST.name())).permitAll()
          .requestMatchers(new AntPathRequestMatcher(
                  Constant.TOKEN_URL, HttpMethod.DELETE.name())).permitAll()
          .requestMatchers(new AntPathRequestMatcher(
                  Constant.SIGNUP_URL, HttpMethod.POST.name())).permitAll()
          .requestMatchers(new AntPathRequestMatcher(
                  Constant.REFRESH_URL, HttpMethod.POST.name())).permitAll()
          .requestMatchers(new AntPathRequestMatcher(
                  Constant.REFRESH_URL, HttpMethod.POST.name())).permitAll()
          .requestMatchers("/api/v1/user/**").permitAll()
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

  private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter authorityConverter =
            new JwtGrantedAuthoritiesConverter();
    authorityConverter.setAuthorityPrefix(Constant.AUTHORITY_PREFIX);
    authorityConverter.setAuthoritiesClaimName(Constant.ROLE_CLAIM);
    JwtAuthenticationConverter converter =
            new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(authorityConverter);
    return converter;
  }

  @Bean
  public JwtDecoder jwtDecoder(RSAPublicKey rsaPublicKey) {
    return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
  }

}
