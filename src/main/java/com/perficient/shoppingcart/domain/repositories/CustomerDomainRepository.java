package com.perficient.shoppingcart.domain.repositories;

import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CustomerDomainRepository {
    void save(@NotNull @Valid  CustomerDomain customerDomain);

    CustomerDomain findByEmail(@NotNull String email);
}
