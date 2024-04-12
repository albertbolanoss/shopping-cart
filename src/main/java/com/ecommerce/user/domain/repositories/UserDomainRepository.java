package com.ecommerce.user.domain.repositories;

import com.ecommerce.user.domain.valueobjects.NewUserDomain;
import com.ecommerce.user.domain.valueobjects.UserPageDomain;
import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserDomainRepository {
    void save(@NotNull @Valid NewUserDomain newUserDomain);

    Optional<NewUserDomain> findByEmail(@NotNull String email);
    UserPageDomain findByFilters(@NotNull UserReqFilterDomain userReqFilterDomain);
}
