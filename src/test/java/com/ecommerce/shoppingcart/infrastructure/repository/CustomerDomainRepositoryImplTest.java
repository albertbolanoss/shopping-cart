package com.ecommerce.shoppingcart.infrastructure.repository;

import com.ecommerce.shoppingcart.domain.valueobjects.CustomerReqFilterDomain;
import com.ecommerce.shoppingcart.infrastructure.api.pageable.PageRequestCreator;
import com.ecommerce.shoppingcart.infrastructure.entities.Customer;
import com.ecommerce.shoppingcart.infrastructure.mother.CustomerDomainMother;
import com.ecommerce.shoppingcart.infrastructure.mother.CustomerMother;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerDomainRepositoryImplTest {
    @InjectMocks
    private CustomerDomainRepositoryImpl customerDomainRepositoryImpl;

    @Mock
    private CustomerRepository customerRepository;

    @Captor
    ArgumentCaptor<Customer> customerEntityArgCaptor;

    @Test
    void save() {
        var customerDomain = CustomerDomainMother.randomNewCustomer();
        customerDomainRepositoryImpl.save(customerDomain);

        verify(customerRepository).save(customerEntityArgCaptor.capture());
        var actualCustomer = customerEntityArgCaptor.getValue();

        verify(customerRepository, atLeastOnce()).save(any(Customer.class));
        assertNotNull(actualCustomer);
    }

    @Test
    void findByEmail() {
        var customer = CustomerMother.random();

        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(customer));

        var actual = customerDomainRepositoryImpl.findByEmail(customer.getEmail());

        assertNotNull(actual);
        assertEquals(customer.getEmail(), actual.getEmail());
    }

    @Test
    void findByEmailEmpty() {
        var customer = CustomerMother.random();

        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        var actual = customerDomainRepositoryImpl.findByEmail(customer.getEmail());

        assertNull(actual);
    }

    @Test
    void findCustomers() {
        var sort = Arrays.asList("field1_asc", "field2_desc");
        var pageNumber = 1;
        var pageSize = 2;
        var pageable = PageRequestCreator.create(pageNumber, pageSize, sort);
        var customers = Arrays.asList(
                CustomerMother.random(),
                CustomerMother.random(),
                CustomerMother.random());
        var customerRepository = mock(CustomerRepository.class);
        var customersPage = new PageImpl<>(customers, pageable, customers.size());
        var customerReqFilterDomain = new CustomerReqFilterDomain(
                "firstName", "lastName", "email", pageNumber, pageSize, sort);

        when(customerRepository.findByCustomersByFirstNameLastNameEmail(anyString(),anyString(),anyString(), any()))
                .thenReturn(customersPage);

        var customerDomainRepositoryImpl = new CustomerDomainRepositoryImpl(customerRepository);
        var actual = customerDomainRepositoryImpl.findByFilters(customerReqFilterDomain);

        assertNotNull(actual);
    }
}
