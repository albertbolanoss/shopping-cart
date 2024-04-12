package com.ecommerce.user.infrastructure.mappers;

import com.ecommerce.user.domain.valueobjects.NewUserDomain;
import com.ecommerce.user.infrastructure.entities.User;
import com.perficient.shoppingcart.application.api.model.AddUserReq;

import java.util.Optional;

public class NewUserDomainMapper {
    public static NewUserDomain convertFromAddUserReq(AddUserReq addUserReq) {
        return Optional.ofNullable(addUserReq)
            .map(apiModel ->
                    new NewUserDomain(
                            apiModel.getFirstName(),
                            apiModel.getLastName(),
                            apiModel.getEmail(),
                            apiModel.getPassword(),
                            apiModel.getPhone()))
                .orElse(null);
    }

    public static NewUserDomain convertFromEntity(User user) {
        return Optional.ofNullable(user)
                .map(entity ->
                        new NewUserDomain(
                                entity.getFirstName(),
                                entity.getLastName(),
                                entity.getEmail(),
                                entity.getPassword(),
                                entity.getPhone()))
                .orElse(null);
    }
}
