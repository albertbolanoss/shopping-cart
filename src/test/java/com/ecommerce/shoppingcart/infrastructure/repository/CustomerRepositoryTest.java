package com.ecommerce.shoppingcart.infrastructure.repository;

import com.ecommerce.customer.infrastructure.repository.CustomerRepository;
import com.ecommerce.shared.api.pageable.PageRequestCreator;
import com.ecommerce.customer.infrastructure.entities.Customer;
import com.ecommerce.shoppingcart.infrastructure.mother.CustomerMother;
import com.ecommerce.shoppingcart.infrastructure.mother.FakerMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    private Customer firstCustomer;

    private Customer secondCustomer;

    private Customer thirdCustomer;

    @BeforeEach
    void init() {
        firstCustomer = CustomerMother.random();
        secondCustomer = CustomerMother.random();
        thirdCustomer = CustomerMother.random();

        customerRepository.save(firstCustomer);
        customerRepository.save(secondCustomer);
        customerRepository.save(thirdCustomer);
    }

    @AfterEach
    void cleanUp() {
        customerRepository.deleteAll();
    }

    @Test
    void findByEmail() {
        var emailFirstCustomerEmail = firstCustomer.getEmail();
        var emailSecondCustomerEmail = secondCustomer.getEmail();
        var unexpectedCustomerEmail = FakerMother.getFaker().internet().emailAddress();

        var expectedFirstCustomer = customerRepository.findByEmail(emailFirstCustomerEmail);
        var expectedSecondCustomer = customerRepository.findByEmail(emailSecondCustomerEmail);
        var unexpectedCustomer = customerRepository.findByEmail(unexpectedCustomerEmail);

        assertTrue(expectedFirstCustomer.isPresent());
        assertTrue(expectedSecondCustomer.isPresent());
        assertTrue(unexpectedCustomer.isEmpty());
    }

    @Test
    void findByCustomersOrderByLastNameAllFilter() {
        var firstName = secondCustomer.getFirstName();
        var lastName = secondCustomer.getLastName();
        var email = secondCustomer.getEmail();

        var pageable = PageRequest.of(0, 10);
        var customers = customerRepository.findByCustomersByFirstNameLastNameEmail(firstName, lastName, email, pageable);

        assertFalse(customers.isEmpty());
    }

    @Test
    void findByCustomersOrderByLastNameByFirstAndLastNamesAndEmptyEmail() {
        var firstName = secondCustomer.getFirstName();
        var lastName = secondCustomer.getLastName();
        var email = "";

        var pageable = PageRequest.of(0, 10);
        var customers = customerRepository.findByCustomersByFirstNameLastNameEmail(firstName, lastName, email, pageable);

        assertFalse(customers.isEmpty());
    }

    @Test
    void findByCustomersOrderByLastNameByFirstAndLastNamesAndNullEmail() {
        var firstName = secondCustomer.getFirstName();
        var lastName = secondCustomer.getLastName();

        var pageable = PageRequest.of(0, 10);
        var customers = customerRepository.findByCustomersByFirstNameLastNameEmail(firstName, lastName, null, pageable);

        assertFalse(customers.isEmpty());
    }

    @Test
    void findByCustomersOrderByLastNameByEmailAndEmptyNames() {
        var firstName = "";
        var lastName = "";
        var email = secondCustomer.getEmail();

        var pageable = PageRequest.of(0, 10);
        var customers = customerRepository.findByCustomersByFirstNameLastNameEmail(firstName, lastName, email, pageable);

        assertFalse(customers.isEmpty());
    }

    @Test
    void findByCustomersOrderByLastNameByEmailAndNullNames() {
        var email = secondCustomer.getEmail();

        var pageable = PageRequest.of(0, 10);
        var customers = customerRepository
                .findByCustomersByFirstNameLastNameEmail(null, null, email, pageable);

        assertFalse(customers.isEmpty());
    }

    @Test
    void findByCustomersOrderByLastNameByEmailPageableAndSort() {
        var sortFieldWithDirection = Arrays.asList("lastName_desc", "lastName_asc");

        var pageable = PageRequestCreator.create(1, 2, sortFieldWithDirection);
        var customers = customerRepository
                .findByCustomersByFirstNameLastNameEmail(null, null, null, pageable);

        assertFalse(customers.isEmpty());
        assertFalse(customers.getContent().isEmpty());
        assertTrue(customers.getContent().size() == 1);
    }
}
