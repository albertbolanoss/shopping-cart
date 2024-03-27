package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.exceptions.AlreadyExistException;
import com.perficient.shoppingcart.domain.repositories.CustomerDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

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
     * @param customerDomain the customer to add
     */
    public void register(@NotNull @Valid CustomerDomain customerDomain) {
        Optional.ofNullable(customerDomainRepository.findByEmail(customerDomain.getEmail()))
            .ifPresent((value) -> {
                var message = String.format("The customer email (%s) is already registered", value.getEmail());
                throw new AlreadyExistException(message);
            });

        customerDomainRepository.save(customerDomain);
    }
}
