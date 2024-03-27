package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.domain.repositories.CustomerDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;
import com.perficient.shoppingcart.infrastructure.mappers.CustomerDomainMapper;
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
     * @param customerDomain the customer domain
     */
    @Override
    public void save(CustomerDomain customerDomain) {
        var customerEntity = CustomerEntityMapper.convertFromDomain(customerDomain);
        customerRepository.save(customerEntity);
    }

    /**
     * Find the first customer with the email
     * @param email the email to search
     * @return a customer domain
     */
    @Override
    public CustomerDomain findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(CustomerDomainMapper::convertFromEntity)
                .orElse(null);
    }

}
