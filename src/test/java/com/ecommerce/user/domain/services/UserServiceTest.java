package com.ecommerce.user.domain.services;

import com.ecommerce.shared.domain.exceptions.AlreadyExistException;
import com.ecommerce.shared.domain.valueobjects.PageResponseDomain;
import com.ecommerce.user.domain.repositories.UserDomainRepository;
import com.ecommerce.user.domain.valueobjects.UserPageDomain;
import com.ecommerce.user.infrastructure.mother.UserDomainMother;
import com.ecommerce.user.infrastructure.mother.UserReqFilterDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
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
class UserServiceTest {
    private UserService userService;

    @Mock
    private UserDomainRepository userDomainRepository;

    @Captor
    ArgumentCaptor<String> emailArgCaptor;

    @BeforeEach
    void init() {
        userService = new UserService(userDomainRepository);
    }

    @Test
    void registerSuccessfully() {
        var expectedUserDomain = UserDomainMother.randomNewCustomer();

        when(userDomainRepository.findByEmail(anyString())).thenReturn(null);

        userService.register(expectedUserDomain);

        verify(userDomainRepository).findByEmail(emailArgCaptor.capture());
        var actualEmail = emailArgCaptor.getValue();

        assertEquals(expectedUserDomain.getEmail(), actualEmail);
    }

    @Test
    void registerThrowAlreadyExistException() {
        var expectedUserDomain = UserDomainMother.randomNewCustomer();

        when(userDomainRepository.findByEmail(anyString())).thenReturn(expectedUserDomain);

        assertThrows(AlreadyExistException.class,
                () -> userService.register(expectedUserDomain));
    }

    @Test
    void findByFilters() {
        var customerReqFilterDomain = UserReqFilterDomainMother.random();
        var pageResponseDomain = new PageResponseDomain(1, 1);
        var users = Arrays.asList(
                UserDomainMother.random(),
                UserDomainMother.random(),
                UserDomainMother.random()
        );
        var userPageDomain = new UserPageDomain(users, pageResponseDomain, customerReqFilterDomain);

        when(userDomainRepository.findByFilters(any())).thenReturn(userPageDomain);

        var actual = userService.findByFilters(customerReqFilterDomain);

        assertNotNull(actual);
        assertNotNull(actual.getUserDomains());
        assertNotNull(actual.getPageResponseDomain());
        assertNotNull(actual.getUserReqFilterDomain());
    }

    @Test
    void findByFiltersNoFoundRecords() {
        var userReqFilterDomain = UserReqFilterDomainMother.random();
        var pageResponseDomain = new PageResponseDomain(0, 0);
        var userPageDomain = new UserPageDomain(new ArrayList<>(), pageResponseDomain, userReqFilterDomain);

        when(userDomainRepository.findByFilters(any())).thenReturn(userPageDomain);

        var actual = userService.findByFilters(userReqFilterDomain);

        assertNotNull(actual);
    }
}
