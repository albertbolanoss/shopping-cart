package com.ecommerce.user.application;

import com.ecommerce.shared.domain.exceptions.AlreadyExistException;
import com.ecommerce.user.domain.services.UserService;
import com.ecommerce.user.domain.valueobjects.UserDomain;
import com.ecommerce.user.infrastructure.mother.UserDomainMother;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegisterUserAppTest {
    private RegisterUserApp registerUserApp;

    @Mock
    private UserService userService;

    @Captor
    ArgumentCaptor<UserDomain> userDomainArgCaptor;

    @BeforeEach
    void init() {
        registerUserApp = new RegisterUserApp(userService);
    }

    @Test
    void register() {
        var expectedUserDomain = UserDomainMother.randomNewCustomer();

        registerUserApp.register(expectedUserDomain);

        verify(userService).register(userDomainArgCaptor.capture());
        UserDomain actualUserDomain = userDomainArgCaptor.getValue();

        assertEquals(expectedUserDomain, actualUserDomain);
    }

    @Test
    void registerShouldThrowHttpClientErrorExceptionWithAlreadyExist() {
        var expectedUserDomain = UserDomainMother.randomNewCustomer();

        doThrow(new AlreadyExistException("customer already exist"))
                .when(userService).register(any(UserDomain.class));

        assertThrows(HttpClientErrorException.class,
                () -> registerUserApp.register(expectedUserDomain));
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
