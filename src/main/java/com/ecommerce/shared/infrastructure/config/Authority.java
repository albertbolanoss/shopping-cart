package com.ecommerce.shared.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
  ADMIN,
  ADMIN_READ,
  ADMIN_WRITE,
  CART,
  CART_READ,
  CART_WRITE;

  private static final String READ = "READ";
  private static final String WRITE = "WRITE";

  private static final String AUTHORITY_FORMAT = "%s_%s";


  @JsonCreator
  public static Authority fromAuthority(String authority) {
    for (Authority b : Authority.values()) {
      if (b.name().equals(authority)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + authority + "'");
  }


  public static String[] getReadAuthorities(Authority authority) {
    var fullAccess = authority.name();
    var readAccess = fromAuthority(String.format(AUTHORITY_FORMAT, fullAccess, READ)).getAuthority();
    var writeAccess = fromAuthority(String.format(AUTHORITY_FORMAT, fullAccess, WRITE)).getAuthority();

    return new String[] { fullAccess, readAccess, writeAccess };
  }

  public static String[] getWriteAuthorities(Authority authority) {
    var fullAccess = authority.name();
    var writeAccess = fromAuthority(String.format(AUTHORITY_FORMAT, fullAccess, WRITE)).getAuthority();

    return new String[] { fullAccess, writeAccess };
  }

  @Override
  public String getAuthority() {
    return this.name();
  }
}
