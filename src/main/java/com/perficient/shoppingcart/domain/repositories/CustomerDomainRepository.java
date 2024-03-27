package com.perficient.shoppingcart.domain.repositories;

import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;

import java.util.Optional;

public interface CustomerDomainRepository {
    void save(CustomerDomain customerDomain);

    CustomerDomain findByEmail(String email);
}
