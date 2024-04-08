package com.ecommerce.user.application;

import com.ecommerce.user.domain.services.UserService;
import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import com.ecommerce.user.infrastructure.mother.UserReqFilterDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
class GetUsersByFiltersAppTest {
    private GetUsersByFiltersApp getUsersByFiltersApp;

    @Mock
    private UserService userService;

    @BeforeEach
    void init() {
        getUsersByFiltersApp = new GetUsersByFiltersApp(userService);
    }

    @Test
    void findByFilterSuccessfully() {
        var customerReqFilterDomain = UserReqFilterDomainMother.random();

        getUsersByFiltersApp.findByFilter(customerReqFilterDomain);

        verify(userService, times(1))
                .findByFilters(any(UserReqFilterDomain.class));

    }
}
