package com.ecommerce.user.infrastructure.mappers;

import com.ecommerce.user.domain.valueobjects.NewUserDomain;
import com.ecommerce.user.infrastructure.entities.User;

import java.util.Optional;

public class UserEntityMapper {
    public static User convertFromDomain(NewUserDomain newUserDomain) {
        return Optional.ofNullable(newUserDomain)
                .map(domain -> new User(
                        null,
                        domain.getFirstName(),
                        domain.getLastName(),
                        domain.getEmail(),
                        domain.getPassword(),
                        domain.getPhone(),
                        domain.isActive()))
                .orElse(null);
    }
}
