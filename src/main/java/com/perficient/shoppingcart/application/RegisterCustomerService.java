package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.services.CustomerService;
import com.perficient.shoppingcart.domain.valueobjects.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterCustomerService {
    /**
     * The customer service domain
     */
    private final CustomerService customerService;

    /**
     *
     * @param customerService customer service domain
     */
    @Autowired
    public RegisterCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Register a customer domain
     * @param customer the customer to register
     */
    public void register(Customer customer) {
        customerService.register(customer);
    }
}
