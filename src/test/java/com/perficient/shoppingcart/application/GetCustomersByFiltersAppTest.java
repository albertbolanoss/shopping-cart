package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.services.CustomerService;
import com.perficient.shoppingcart.domain.valueobjects.CustomerReqFilterDomain;
import com.perficient.shoppingcart.infrastructure.mother.CustomerReqFilterDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
class GetCustomersByFiltersAppTest {
    private GetCustomersByFiltersApp getCustomersByFiltersService;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    void init() {
        getCustomersByFiltersService = new GetCustomersByFiltersApp(customerService);
    }

    @Test
    void findByFilterSuccessfully() {
        var customerReqFilterDomain = CustomerReqFilterDomainMother.random();

        getCustomersByFiltersService.findByFilter(customerReqFilterDomain);

        verify(customerService, times(1))
                .findByFilters(any(CustomerReqFilterDomain.class));

    }
}
