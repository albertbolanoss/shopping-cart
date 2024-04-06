package com.ecommerce.shoppingcart.application;

import com.ecommerce.shoppingcart.domain.services.CustomerService;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerPageDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerReqFilterDomain;
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
