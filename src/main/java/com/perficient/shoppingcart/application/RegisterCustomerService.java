package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.application.api.model.AddCustomerReq;
import com.perficient.shoppingcart.domain.services.CustomerService;
import com.perficient.shoppingcart.infrastructure.mappers.CustomerDomainMapper;
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
     * @param addCustomerReq the add customer request
     */
    public void register(AddCustomerReq addCustomerReq) {
        var customerDomain = CustomerDomainMapper.convertFromARequest(addCustomerReq);
        customerService.register(customerDomain);
    }
}
