package com.ecommerce.user.domain.repositories;

import com.ecommerce.user.domain.valueobjects.UserDomain;
import com.ecommerce.user.domain.valueobjects.UserPageDomain;
import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserDomainRepository {
    void save(@NotNull @Valid UserDomain userDomain);

    Optional<UserDomain> findByEmail(@NotNull String email);
    UserPageDomain findByFilters(@NotNull UserReqFilterDomain userReqFilterDomain);
}
