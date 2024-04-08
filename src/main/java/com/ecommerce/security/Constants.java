package com.ecommerce.security;

/**
 * @author : github.com/sharmasourabh
 * @project : Chapter06 - Modern API Development with Spring and Spring Boot Ed 2
 */
public class Constants {
  public static final String ENCODER_ID = "bcrypt";
  public static final String API_URL_PREFIX = "/api/v1/**";
  public static final String H2_URL_PREFIX = "/h2-console/**";
  public static final String SIGNUP_URL = "/api/v1/users";
  public static final String TOKEN_URL = "/api/v1/auth/token";
  public static final String REFRESH_URL = "/api/v1/auth/token/refresh";
  public static final String PRODUCT_URL = "/api/v1/product/379fbb01-ff84-4a61-8856-afa2d52b3974/item";

  public static final String ITEMS_URL = "/api/v1/items";

  public static final String CHECKOUT = "/api/v1/checkout";

  public static final String SWAGGER_URL = "/swagger-ui/index.html";
  public static final String AUTHORIZATION = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String SECRET_KEY = "SECRET_KEY";
  public static final long EXPIRATION_TIME = 900_000; // 15 mins
  public static final String ROLE_CLAIM = "roles";
  public static final String AUTHORITY_PREFIX = "ROLE_";
}
