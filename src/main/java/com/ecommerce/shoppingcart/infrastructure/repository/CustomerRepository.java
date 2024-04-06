package com.ecommerce.shoppingcart.infrastructure.repository;

import com.ecommerce.shoppingcart.infrastructure.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {
    @Query("SELECT c from Customer c WHERE c.email = :email")
    Optional<Customer> findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE" +
            " c.active = true" +
            " AND (:firstName IS NULL OR c.firstName LIKE %:firstName%)" +
            " AND (:lastName IS NULL OR c.lastName LIKE %:lastName%)" +
            " AND (:email IS NULL OR c.email LIKE %:email%)")
    Page<Customer> findByCustomersByFirstNameLastNameEmail(String firstName, String lastName, String email, Pageable pageable);
}
