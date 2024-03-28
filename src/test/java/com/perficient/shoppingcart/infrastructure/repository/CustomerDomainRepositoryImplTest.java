package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.domain.valueobjects.PageRequestDomain;
import com.perficient.shoppingcart.infrastructure.api.pageable.PageRequestCreator;
import com.perficient.shoppingcart.infrastructure.entities.Customer;
import com.perficient.shoppingcart.infrastructure.mappers.PageRequestMapper;
import com.perficient.shoppingcart.infrastructure.mother.CustomerDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.CustomerMother;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    /*
    @Test
    void findCustomers() {
        var customerDomain = CustomerDomainMother.randomNewCustomer();
        var sort = Arrays.asList("field1_asc", "field2_desc");
        var pageNumber = 0;
        var pageSize = 10;
        var pageRequestDomain = new PageRequestDomain(pageNumber, pageSize, sort);
        var pageable = PageRequestCreator.create(pageNumber, pageSize, sort);
        var customers = Arrays.asList(
                CustomerMother.random(),
                CustomerMother.random(),
                CustomerMother.random());

        var customersPage = new PageImpl<>(customers, pageable, customers.size());

        when(customerRepository.findByCustomersByFirstNameLastNameEmail(anyString(),anyString(),anyString(), any()))
                .thenReturn(customersPage);

        var actual = customerDomainRepositoryImpl.findCustomers(customerDomain, pageRequestDomain);

        assertNotNull(actual);
    }
     */

    @Test
    void findCustomers() {
        var customerDomain = CustomerDomainMother.randomNewCustomer();
        var sort = Arrays.asList("field1_asc", "field2_desc");
        var pageNumber = 1;
        var pageSize = 2;
        var expectedTotalPages = 2;
        var expectedTotalItemsByCurrentPage = 1;
        var pageRequestDomain = new PageRequestDomain(pageNumber, pageSize, sort);
        var pageable = PageRequestCreator.create(pageNumber, pageSize, sort);
        var customers = Arrays.asList(
                CustomerMother.random(),
                CustomerMother.random(),
                CustomerMother.random());
        var customerRepository = mock(CustomerRepository.class);
        var customersPage = new PageImpl<>(customers, pageable, customers.size());
        when(customerRepository.findByCustomersByFirstNameLastNameEmail(anyString(),anyString(),anyString(), any()))
                .thenReturn(customersPage);

        var customerDomainRepositoryImpl = new CustomerDomainRepositoryImpl(customerRepository);
        var actual = customerDomainRepositoryImpl.findCustomers(customerDomain, pageRequestDomain);

        assertNotNull(actual);
        assertNotNull(actual.getPageResponseDomain());
        // assertEquals(customers.size(), actual.getPageResponseDomain().getTotalItems());
        // assertEquals(expectedTotalPages, actual.getPageResponseDomain().getTotalPages());
        assertNotNull(actual.getPageResponseDomain().getPageRequestDomain());
        // assertEquals(expectedTotalPages, actual.getPageResponseDomain().getPageRequestDomain().getPageNumber());
        assertEquals(expectedTotalPages, actual.getPageResponseDomain().getPageRequestDomain().getPageSize());
        assertNotNull(actual.getCustomerDomains());
        // assertEquals(expectedTotalItemsByCurrentPage, actual.getCustomerDomains().size());
    }
}
