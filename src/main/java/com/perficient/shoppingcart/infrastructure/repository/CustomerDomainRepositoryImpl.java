package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.domain.repositories.CustomerDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.Customer;
import com.perficient.shoppingcart.infrastructure.mappers.CustomerEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Customer domain repository implementation
 */
@Service
public class CustomerDomainRepositoryImpl implements CustomerDomainRepository {
    /**
     * Customer infrastructure repository
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor
     * @param customerRepository customer infrastructure repository
     */
    @Autowired
    public CustomerDomainRepositoryImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer domain in database
     * @param customer the customer domain
     */
    @Override
    public void save(Customer customer) {
        var customerEntity = CustomerEntityMapper.convertFromDomain(customer);
        customerRepository.save(customerEntity);
    }
}
