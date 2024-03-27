package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.exceptions.AlreadyExistException;
import com.perficient.shoppingcart.domain.repositories.CustomerDomainRepository;
import com.perficient.shoppingcart.infrastructure.mother.CustomerDomainMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}
