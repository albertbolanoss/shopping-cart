package com.ecommerce.customer.domain.services;

import com.ecommerce.customer.domain.valueobjects.CustomerDomain;
import com.ecommerce.customer.domain.valueobjects.CustomerPageDomain;
import com.ecommerce.customer.domain.valueobjects.CustomerReqFilterDomain;
import com.ecommerce.shared.domain.exceptions.AlreadyExistException;
import com.ecommerce.customer.domain.repositories.CustomerDomainRepository;
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
        if (customerDomain.getFirstName() == null
                || customerDomain.getLastName() == null
                || customerDomain.getEmail() == null)
            throw new IllegalArgumentException("Missing required customer information (first name, last name, email)");


        Optional.ofNullable(customerDomainRepository.findByEmail(customerDomain.getEmail()))
            .ifPresent(value -> {
                var message = String.format("The customer email (%s) is already registered", value.getEmail());
                throw new AlreadyExistException(message);
            });

        customerDomainRepository.save(customerDomain);
    }

    /**
     * Get the customer filter by first name, last name, email and page parameters
     * @param customerReqFilterDomain the customer request filter domain
     * @return a customer page domain
     */
    public CustomerPageDomain findByFilters(CustomerReqFilterDomain customerReqFilterDomain) {
        return customerDomainRepository.findByFilters(customerReqFilterDomain);
    }
}
