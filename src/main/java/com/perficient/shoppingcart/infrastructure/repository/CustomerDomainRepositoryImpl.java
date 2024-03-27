package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.domain.repositories.CustomerDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;
import com.perficient.shoppingcart.infrastructure.mappers.CustomerDomainMapper;
import com.perficient.shoppingcart.infrastructure.mappers.CustomerEntityMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Customer domain repository implementation
 */
@Service
@Validated
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
    public CustomerDomainRepositoryImpl(@NotNull @Valid CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer domain in database
     * @param customerDomain the customer domain
     */
    @Override
    public void save(@NotNull @Valid CustomerDomain customerDomain) {
        var customerEntity = CustomerEntityMapper.convertFromDomain(customerDomain);
        customerRepository.save(customerEntity);
    }

    /**
     * Find the first customer with the email
     * @param email the email to search
     * @return a customer domain
     */
    @Override
    public CustomerDomain findByEmail(@NotNull @NotBlank String email) {
        return customerRepository.findByEmail(email)
                .map(CustomerDomainMapper::convertFromEntity)
                .orElse(null);
    }

}
