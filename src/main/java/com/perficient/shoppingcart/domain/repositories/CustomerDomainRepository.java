package com.perficient.shoppingcart.domain.repositories;

import com.perficient.shoppingcart.domain.valueobjects.Customer;
import org.springframework.stereotype.Service;

public interface CustomerDomainRepository {
    void save(Customer customer);
}
