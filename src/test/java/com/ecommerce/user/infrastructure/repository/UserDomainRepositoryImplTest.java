package com.ecommerce.user.infrastructure.repository;

import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import com.ecommerce.user.infrastructure.entities.User;
import com.ecommerce.user.infrastructure.mother.UserDomainMother;
import com.ecommerce.user.infrastructure.mother.UserMother;
import com.ecommerce.shared.api.pageable.PageRequestCreator;
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
class UserDomainRepositoryImplTest {
    @InjectMocks
    private UserDomainRepositoryImpl userDomainRepositoryImpl;

    @Mock
    private UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> userEntityArgCaptor;

    @Test
    void save() {
        var userDomain = UserDomainMother.randomNewCustomer();
        userDomainRepositoryImpl.save(userDomain);

        verify(userRepository).save(userEntityArgCaptor.capture());
        var actualUser = userEntityArgCaptor.getValue();

        verify(userRepository, atLeastOnce()).save(any(User.class));
        assertNotNull(actualUser);
    }

    @Test
    void findByEmail() {
        var user = UserMother.random();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        var actual = userDomainRepositoryImpl.findByEmail(user.getEmail());

        assertNotNull(actual);
        assertEquals(user.getEmail(), actual.getEmail());
    }

    @Test
    void findByEmailEmpty() {
        var user = UserMother.random();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        var actual = userDomainRepositoryImpl.findByEmail(user.getEmail());

        assertNull(actual);
    }

    @Test
    void findCustomers() {
        var sort = Arrays.asList("field1_asc", "field2_desc");
        var pageNumber = 1;
        var pageSize = 2;
        var pageable = PageRequestCreator.create(pageNumber, pageSize, sort);
        var users = Arrays.asList(
                UserMother.random(),
                UserMother.random(),
                UserMother.random());
        var userRepository = mock(UserRepository.class);
        var usersPage = new PageImpl<>(users, pageable, users.size());
        var userReqFilterDomain = new UserReqFilterDomain(
                "firstName", "lastName", "email", pageNumber, pageSize, sort);

        when(userRepository.findByCustomersByFirstNameLastNameEmail(anyString(),anyString(),anyString(), any()))
                .thenReturn(usersPage);

        var userDomainRepositoryImpl = new UserDomainRepositoryImpl(userRepository);
        var actual = userDomainRepositoryImpl.findByFilters(userReqFilterDomain);

        assertNotNull(actual);
    }
}
