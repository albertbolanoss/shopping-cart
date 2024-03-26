package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.domain.repositories.CustomerDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.Customer;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerDomainRepositoryImpl implements CustomerDomainRepository {
    private final UserRepository userRepository;

    @Autowired
    public CustomerDomainRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(Customer customer) {

    }
}
