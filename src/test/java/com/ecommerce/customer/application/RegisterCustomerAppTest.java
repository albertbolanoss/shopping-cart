package com.ecommerce.customer.application;

import com.ecommerce.shared.domain.exceptions.AlreadyExistException;
import com.ecommerce.customer.domain.services.CustomerService;
import com.ecommerce.customer.domain.valueobjects.CustomerDomain;
import com.ecommerce.customer.infrastructure.mother.CustomerDomainMother;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RegisterCustomerAppTest {
    @InjectMocks
    private RegisterCustomerApp registerCustomerService;

    @Mock
    private CustomerService customerService;

    @Captor
    ArgumentCaptor<CustomerDomain> customerDomainArgCaptor;

    @Test
    void register() {
        var expetedCustomerDomain = CustomerDomainMother.randomNewCustomer();

        registerCustomerService.register(expetedCustomerDomain);

        verify(customerService).register(customerDomainArgCaptor.capture());
        CustomerDomain actualCustomerDomain = customerDomainArgCaptor.getValue();

        assertEquals(expetedCustomerDomain, actualCustomerDomain);
    }

    @Test
    void registerShouldThrowHttpClientErrorExceptionWithAlreadyExist() {
        var expetedCustomerDomain = CustomerDomainMother.randomNewCustomer();

        doThrow(new AlreadyExistException("customer already exist"))
                .when(customerService).register(any(CustomerDomain.class));

        assertThrows(HttpClientErrorException.class,
                () -> registerCustomerService.register(expetedCustomerDomain));
    }

    @Test
    void registerShouldThrowHttpClientErrorExceptionWithConstraintViolation() {
        var expetedCustomerDomain = CustomerDomainMother.randomNewCustomer();

        doThrow(new ConstraintViolationException(null))
                .when(customerService).register(any(CustomerDomain.class));

        assertThrows(HttpClientErrorException.class,
                () -> registerCustomerService.register(expetedCustomerDomain));
    }
}
