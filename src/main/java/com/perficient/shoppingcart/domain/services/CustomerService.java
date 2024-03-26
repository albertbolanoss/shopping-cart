package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.repositories.CustomerDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Customer domain services
 */
@Service
@Validated
public class CustomerService {
    /**
     * The Customer domain repository
     */
    private  final CustomerDomainRepository customerDomainRepository;

    /**
     * Constructor
     * @param customerDomainRepository customer domain repository
     */
    @Autowired
    public CustomerService(CustomerDomainRepository customerDomainRepository) {
        this.customerDomainRepository = customerDomainRepository;
    }

    /**
     * Register a new customer to the business
     * @param customer the customer to add
     */
    public void register(@NotNull @Valid Customer customer) {
        customerDomainRepository.save(customer);
    }
}
