package com.ecommerce.user.infrastructure.repository;

import com.ecommerce.user.infrastructure.entities.User;
import com.ecommerce.user.infrastructure.mother.UserMother;
import com.ecommerce.shared.api.pageable.PageRequestCreator;
import com.ecommerce.shared.infrastructure.mother.FakerMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User firstUser;

    private User secondUser;

    private User thirdUser;

    @BeforeEach
    void init() {
        firstUser = UserMother.random();
        secondUser = UserMother.random();
        thirdUser = UserMother.random();

        userRepository.save(firstUser);
        userRepository.save(secondUser);
        userRepository.save(thirdUser);
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        var emailFirstUserEmail = firstUser.getEmail();
        var emailSecondUserEmail = secondUser.getEmail();
        var unexpectedUserEmail = FakerMother.getFaker().internet().emailAddress();

        var expectedFirstUser = userRepository.findByEmail(emailFirstUserEmail);
        var expectedSecondUser = userRepository.findByEmail(emailSecondUserEmail);
        var unexpectedUser = userRepository.findByEmail(unexpectedUserEmail);

        assertTrue(expectedFirstUser.isPresent());
        assertTrue(expectedSecondUser.isPresent());
        assertTrue(unexpectedUser.isEmpty());
    }

    @Test
    void findByUsersOrderByLastNameAllFilter() {
        var firstName = secondUser.getFirstName();
        var lastName = secondUser.getLastName();
        var email = secondUser.getEmail();

        var pageable = PageRequest.of(0, 10);
        var users = userRepository.findByCustomersByFirstNameLastNameEmail(firstName, lastName, email, pageable);

        assertFalse(users.isEmpty());
    }

    @Test
    void findByUsersOrderByLastNameByFirstAndLastNamesAndEmptyEmail() {
        var firstName = secondUser.getFirstName();
        var lastName = secondUser.getLastName();
        var email = "";

        var pageable = PageRequest.of(0, 10);
        var users = userRepository.findByCustomersByFirstNameLastNameEmail(firstName, lastName, email, pageable);

        assertFalse(users.isEmpty());
    }

    @Test
    void findByUsersOrderByLastNameByFirstAndLastNamesAndNullEmail() {
        var firstName = secondUser.getFirstName();
        var lastName = secondUser.getLastName();

        var pageable = PageRequest.of(0, 10);
        var users = userRepository.findByCustomersByFirstNameLastNameEmail(firstName, lastName, null, pageable);

        assertFalse(users.isEmpty());
    }

    @Test
    void findByUsersOrderByLastNameByEmailAndEmptyNames() {
        var firstName = "";
        var lastName = "";
        var email = secondUser.getEmail();

        var pageable = PageRequest.of(0, 10);
        var users = userRepository.findByCustomersByFirstNameLastNameEmail(firstName, lastName, email, pageable);

        assertFalse(users.isEmpty());
    }

    @Test
    void findByUsersOrderByLastNameByEmailAndNullNames() {
        var email = secondUser.getEmail();

        var pageable = PageRequest.of(0, 10);
        var users = userRepository
                .findByCustomersByFirstNameLastNameEmail(null, null, email, pageable);

        assertFalse(users.isEmpty());
    }

    @Test
    void findByUsersOrderByLastNameByEmailPageableAndSort() {
        var sortFieldWithDirection = Arrays.asList("lastName_desc", "lastName_asc");

        var pageable = PageRequestCreator.create(1, 2, sortFieldWithDirection);
        var users = userRepository
                .findByCustomersByFirstNameLastNameEmail(null, null, null, pageable);

        assertFalse(users.isEmpty());
        assertFalse(users.getContent().isEmpty());
        assertEquals(1, users.getContent().size());
    }
}
