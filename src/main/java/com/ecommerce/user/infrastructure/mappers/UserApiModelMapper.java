package com.ecommerce.user.infrastructure.mappers;

import com.ecommerce.user.domain.valueobjects.UserDomain;
import com.ecommerce.user.domain.valueobjects.UserIdDomain;
import com.perficient.shoppingcart.application.api.model.UserReq;

import java.util.Optional;

/**
 * Convert to customer api model
 */
public class UserApiModelMapper {
    public static UserReq convertFromDomain(UserDomain userDomain) {
        return Optional.ofNullable(userDomain)
                .map(domain -> new UserReq()
                        .id(Optional.ofNullable(domain.getCustomerId())
                                .map(UserIdDomain::getId)
                                .orElse(null))
                        .email(domain.getEmail())
                        .firstName(domain.getFirstName())
                        .lastName(domain.getLastName())
                        .phone(domain.getPhone()))
                .orElse(null);
    }
}
