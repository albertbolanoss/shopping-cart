package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.services.CustomerService;
import com.perficient.shoppingcart.domain.valueobjects.CustomerPageDomain;
import com.perficient.shoppingcart.domain.valueobjects.CustomerReqFilterDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCustomersByFiltersService {
    /**
     * The customer service domain
     */
    private final CustomerService customerService;

    /**
     *
     * @param customerService customer service domain
     */
    @Autowired
    public GetCustomersByFiltersService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerPageDomain findByFilter(CustomerReqFilterDomain customerReqFilterDomain) {
        return customerService.findByFilters(customerReqFilterDomain);
    }
}
