package com.ecommerce.user.infrastructure.mappers;

import com.ecommerce.user.domain.valueobjects.NewUserDomain;
import com.perficient.shoppingcart.application.api.model.UserReq;

import java.util.Optional;

/**
 * Convert to customer api model
 */
public class UserApiModelMapper {
    public static UserReq convertFromDomain(NewUserDomain newUserDomain) {
        return Optional.ofNullable(newUserDomain)
                .map(domain -> new UserReq()
                        .email(domain.getEmail())
                        .firstName(domain.getFirstName())
                        .lastName(domain.getLastName())
                        .phone(domain.getPhone()))
                .orElse(null);
    }
}
