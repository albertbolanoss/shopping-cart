package com.ecommerce.user.infrastructure.repository;

import com.ecommerce.shared.api.pageable.PageRequestCreator;
import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import com.ecommerce.user.infrastructure.entities.User;
import com.ecommerce.user.infrastructure.mother.UserDomainMother;
import com.ecommerce.user.infrastructure.mother.UserMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserModelRepositoryImplTest {
    private UserDomainRepositoryImpl userDomainRepositoryImpl;

    @Mock
    private UserRepository userRepository;

    @Captor
    ArgumentCaptor<User> userEntityArgCaptor;

    @BeforeEach
    void init() {
        userDomainRepositoryImpl = new UserDomainRepositoryImpl(userRepository);
    }

    @Test
    void save() {
        var userDomain = UserDomainMother.randomNewUser();
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

        assertTrue(actual.isPresent());
        assertEquals(user.getEmail(), actual.get().getEmail());
    }

    @Test
    void findByEmailEmpty() {
        var user = UserMother.random();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        var actual = userDomainRepositoryImpl.findByEmail(user.getEmail());

        assertFalse(actual.isPresent());
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
