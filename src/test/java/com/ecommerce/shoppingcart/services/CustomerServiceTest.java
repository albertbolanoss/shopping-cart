package com.ecommerce.shoppingcart.services;

import com.ecommerce.shoppingcart.domain.exceptions.AlreadyExistException;
import com.ecommerce.shoppingcart.domain.repositories.CustomerDomainRepository;
import com.ecommerce.shoppingcart.domain.services.CustomerService;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerPageDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.PageResponseDomain;
import com.ecommerce.shoppingcart.infrastructure.mother.CustomerDomainMother;
import com.ecommerce.shoppingcart.infrastructure.mother.CustomerReqFilterDomainMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDomainRepository customerDomainRepository;

    @Captor
    ArgumentCaptor<String> emailArgCaptor;

    @Test
    void registerSuccessfully() {
        var expectedCustomerDomain = CustomerDomainMother.randomNewCustomer();

        when(customerDomainRepository.findByEmail(anyString())).thenReturn(null);

        customerService.register(expectedCustomerDomain);

        verify(customerDomainRepository).findByEmail(emailArgCaptor.capture());
        var actualEmail = emailArgCaptor.getValue();

        assertEquals(expectedCustomerDomain.getEmail(), actualEmail);
    }

    @Test
    void registerThrowAlreadyExistException() {
        var expectedCustomerDomain = CustomerDomainMother.randomNewCustomer();

        when(customerDomainRepository.findByEmail(anyString())).thenReturn(expectedCustomerDomain);

        assertThrows(AlreadyExistException.class,
                () -> customerService.register(expectedCustomerDomain));
    }

    @Test
    void findByFilters() {
        var customerReqFilterDomain = CustomerReqFilterDomainMother.random();
        var pageResponseDomain = new PageResponseDomain(1, 1);
        var customers = Arrays.asList(
                CustomerDomainMother.random(),
                CustomerDomainMother.random(),
                CustomerDomainMother.random()
        );
        var customerPageDomain = new CustomerPageDomain(customers, pageResponseDomain, customerReqFilterDomain);

        when(customerDomainRepository.findByFilters(any())).thenReturn(customerPageDomain);

        var actual = customerService.findByFilters(customerReqFilterDomain);

        assertNotNull(actual);
        assertNotNull(actual.getCustomerDomains());
        assertNotNull(actual.getPageResponseDomain());
        assertNotNull(actual.getCustomerReqFilterDomain());
    }

    @Test
    void findByFiltersNoFoundRecords() {
        var customerReqFilterDomain = CustomerReqFilterDomainMother.random();
        var pageResponseDomain = new PageResponseDomain(0, 0);
        var customerPageDomain = new CustomerPageDomain(new ArrayList<>(), pageResponseDomain, customerReqFilterDomain);

        when(customerDomainRepository.findByFilters(any())).thenReturn(customerPageDomain);

        var actual = customerService.findByFilters(customerReqFilterDomain);

        assertNotNull(actual);
    }
}
