package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.valueobjects.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Customer domain services
 */
@Service
@Validated
public class CustomerService {
    /**
     * Register a new customer to the business
     * @param customer the customer to add
     */
    public void register(@NotNull @Valid Customer customer) {
        System.out.println("Registration successful!");
    }
}