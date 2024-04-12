package com.ecommerce.user.infrastructure.repository;

import com.ecommerce.user.infrastructure.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    @Query("SELECT c from User c WHERE c.email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT c FROM User c WHERE" +
            " c.active = true" +
            " AND (:firstName IS NULL OR c.firstName LIKE %:firstName%)" +
            " AND (:lastName IS NULL OR c.lastName LIKE %:lastName%)" +
            " AND (:email IS NULL OR c.email LIKE %:email%)")
    Page<User> findByFirstNameLastNameEmail(String firstName, String lastName, String email, Pageable pageable);

}
