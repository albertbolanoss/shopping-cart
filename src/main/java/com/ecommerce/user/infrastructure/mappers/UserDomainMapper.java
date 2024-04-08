package com.ecommerce.user.infrastructure.mappers;

import com.ecommerce.user.domain.valueobjects.UserDomain;
import com.ecommerce.user.domain.valueobjects.UserIdDomain;
import com.ecommerce.user.infrastructure.entities.User;
import com.perficient.shoppingcart.application.api.model.AddUserReq;

import java.util.Optional;

public class UserDomainMapper {
    public static UserDomain convertFromARequest(AddUserReq addUserReq) {
        return Optional.ofNullable(addUserReq)
            .map(addCustomerReq ->
                    new UserDomain(new UserIdDomain(null),
                            addCustomerReq.getFirstName(),
                            addCustomerReq.getLastName(),
                            addCustomerReq.getEmail(),
                            addCustomerReq.getPassword(),
                            addCustomerReq.getPhone(),
                            Boolean.TRUE))
                .orElse(null);
    }

    public static UserDomain convertFromEntity(User user) {
        return Optional.ofNullable(user)
                .map(entity ->
                        new UserDomain(new UserIdDomain(entity.getId()),
                                entity.getFirstName(),
                                entity.getLastName(),
                                entity.getEmail(),
                                entity.getPassword(),
                                entity.getPhone(),
                                entity.isActive()))
                .orElse(null);
    }
}
