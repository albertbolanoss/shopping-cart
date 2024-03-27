package com.perficient.shoppingcart.application;

import com.fasterxml.jackson.core.JsonParseException;
import com.perficient.shoppingcart.domain.exceptions.AlreadyExistException;
import com.perficient.shoppingcart.domain.exceptions.CartAppException;
import com.perficient.shoppingcart.domain.services.CustomerService;
import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
     * @param customerDomain the customer to register
     */
    public void register(CustomerDomain customerDomain) {
        try {
            customerService.register(customerDomain);
        } catch (AlreadyExistException ex) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, ex.getMessage());
        }
    }
}
