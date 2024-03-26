package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.model.CustomerModel;
import com.perficient.shoppingcart.repositories.CustomerModelRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerModelRepositoryImpl implements CustomerModelRepository {
    private final UserRepository userRepository;

    @Autowired
    public CustomerModelRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(CustomerModel customerModel) {
        // var user = CustomerMapper.convert.apply(customer);
        // userRepository.save(user);
    }

}
