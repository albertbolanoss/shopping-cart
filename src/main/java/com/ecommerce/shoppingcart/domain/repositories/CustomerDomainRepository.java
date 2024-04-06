package com.ecommerce.shoppingcart.domain.repositories;

import com.ecommerce.shoppingcart.domain.valueobjects.CustomerDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerPageDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerReqFilterDomain;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CustomerDomainRepository {
    void save(@NotNull @Valid CustomerDomain customerDomain);

    CustomerDomain findByEmail(@NotNull String email);
    CustomerPageDomain findByFilters(@NotNull CustomerReqFilterDomain customerReqFilterDomain);
}
