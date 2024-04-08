package com.ecommerce.user.application;

import com.ecommerce.shared.domain.exceptions.AlreadyExistException;
import com.ecommerce.user.domain.services.UserService;
import com.ecommerce.user.domain.valueobjects.UserDomain;
import com.ecommerce.user.infrastructure.mother.UserDomainMother;
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
class RegisterUserAppTest {
    @InjectMocks
    private RegisterUserApp registerUserApp;

    @Mock
    private UserService userService;

    @Captor
    ArgumentCaptor<UserDomain> userDomainArgCaptor;

    @Test
    void register() {
        var expetedCustomerDomain = UserDomainMother.randomNewCustomer();

        registerUserApp.register(expetedCustomerDomain);

        verify(userService).register(userDomainArgCaptor.capture());
        UserDomain actualUserDomain = userDomainArgCaptor.getValue();

        assertEquals(expetedCustomerDomain, actualUserDomain);
    }

    @Test
    void registerShouldThrowHttpClientErrorExceptionWithAlreadyExist() {
        var expetedCustomerDomain = UserDomainMother.randomNewCustomer();

        doThrow(new AlreadyExistException("customer already exist"))
                .when(userService).register(any(UserDomain.class));

        assertThrows(HttpClientErrorException.class,
                () -> registerUserApp.register(expetedCustomerDomain));
    }

    @Test
    void registerShouldThrowHttpClientErrorExceptionWithConstraintViolation() {
        var expetedCustomerDomain = UserDomainMother.randomNewCustomer();

        doThrow(new ConstraintViolationException(null))
                .when(userService).register(any(UserDomain.class));

        assertThrows(HttpClientErrorException.class,
                () -> registerUserApp.register(expetedCustomerDomain));
    }
}
