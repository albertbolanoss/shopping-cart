package com.ecommerce.user.infrastructure.mappers;

import com.ecommerce.user.domain.valueobjects.UserDomain;
import com.ecommerce.user.domain.valueobjects.UserIdDomain;
import com.ecommerce.user.infrastructure.entities.User;

import java.util.Optional;

public class UserEntityMapper {
    public static User convertFromDomain(UserDomain userDomain) {
        var customerOptional = Optional.ofNullable(userDomain);

        var customerId = customerOptional
                .map(UserDomain::getCustomerId)
                .orElse(null);

        var id = Optional.ofNullable(customerId)
                .map(UserIdDomain::getId)
                .orElse(null);

        var active = customerOptional
                .map(UserDomain::getActive)
                .orElse(false);

        return customerOptional
                .map(domain -> new User(
                        id,
                        domain.getFirstName(),
                        domain.getLastName(),
                        domain.getEmail(),
                        domain.getPassword(),
                        domain.getPhone(),
                        active))
                .orElse(null);
    }
}
