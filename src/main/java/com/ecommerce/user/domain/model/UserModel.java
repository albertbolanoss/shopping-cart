package com.ecommerce.user.domain.model;

import com.ecommerce.user.domain.exceptions.UserAlreadyExist;
import com.ecommerce.user.domain.repositories.UserDomainRepository;
import com.ecommerce.user.domain.valueobjects.NewUserDomain;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class UserModel {
    private final @Valid NewUserDomain newUserDomain;

    private UserDomainRepository userDomainRepository;

    public void register() {
        var existUser =  userDomainRepository.findByEmail(newUserDomain.getEmail());

        if (existUser.isPresent()) {
            throw new UserAlreadyExist(newUserDomain.getEmail());
        }

        userDomainRepository.save(newUserDomain);
    }

}
