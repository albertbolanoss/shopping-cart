package com.perficient.shoppingcart.domain.repositories;

import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;
import com.perficient.shoppingcart.domain.valueobjects.CustomerPageDomain;
import com.perficient.shoppingcart.domain.valueobjects.PageRequestDomain;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface CustomerDomainRepository {
    void save(@NotNull @Valid  CustomerDomain customerDomain);

    CustomerDomain findByEmail(@NotNull String email);
    CustomerPageDomain findCustomers(@NotNull CustomerDomain customerDomain, PageRequestDomain pageRequestDomain);
}
