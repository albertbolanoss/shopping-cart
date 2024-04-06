package com.ecommerce.customer.application;

import com.ecommerce.customer.domain.services.CustomerService;
import com.ecommerce.customer.domain.valueobjects.CustomerPageDomain;
import com.ecommerce.customer.domain.valueobjects.CustomerReqFilterDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCustomersByFiltersApp {
    /**
     * The customer service domain
     */
    private final CustomerService customerService;

    /**
     *
     * @param customerService customer service domain
     */
    @Autowired
    public GetCustomersByFiltersApp(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerPageDomain findByFilter(CustomerReqFilterDomain customerReqFilterDomain) {
        return customerService.findByFilters(customerReqFilterDomain);
    }
}
