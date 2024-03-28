package com.perficient.shoppingcart.infrastructure.repository;

import com.perficient.shoppingcart.infrastructure.entities.Customer;
import com.perficient.shoppingcart.infrastructure.mother.CustomerDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.CustomerMother;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
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


}
