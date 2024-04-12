package com.ecommerce.user.application;

import com.ecommerce.user.domain.repositories.UserDomainRepository;
import com.ecommerce.user.domain.valueobjects.NewUserDomain;
import com.ecommerce.user.infrastructure.mother.UserDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserAppTest {
    private RegisterUserApp registerUserApp;

    @Mock
    private UserDomainRepository userDomainRepository;;

    @Captor
    ArgumentCaptor<NewUserDomain> userDomainArgCaptor;

    @BeforeEach
    void init() {
        registerUserApp = new RegisterUserApp(userDomainRepository);
    }

    @Test
    void register() {
        var expectedUserDomain = UserDomainMother.randomNewUser();

        registerUserApp.register(expectedUserDomain);

        verify(userDomainRepository).save(userDomainArgCaptor.capture());
        NewUserDomain actualNewUserDomain = userDomainArgCaptor.getValue();

        assertEquals(expectedUserDomain, actualNewUserDomain);
    }

    @Test
    void registerShouldThrowHttpClientErrorExceptionWithAlreadyExist() {
        var expectedUserDomain = UserDomainMother.randomNewUser();
        var userDomain = UserDomainMother.random();

        when(userDomainRepository.findByEmail(anyString())).thenReturn(Optional.of(userDomain));

        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class,
                () -> registerUserApp.register(expectedUserDomain));

        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
    }

    @Test
    void registerShouldThrowHttpClientErrorExceptionWithConstraintViolation() {
        var userDomain = UserDomainMother.nullableNewCustomer();


        assertThrows(HttpClientErrorException.class,
                () -> registerUserApp.register(null));
    }
}
