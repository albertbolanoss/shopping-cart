package com.perficient.shoppingcart.domain.repositories;

import com.perficient.shoppingcart.domain.valueobjects.Customer;

public interface CustomerDomainRepository {
    void save(Customer customer);
}
