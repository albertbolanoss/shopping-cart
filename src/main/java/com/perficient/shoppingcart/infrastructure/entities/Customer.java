package com.perficient.shoppingcart.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@Getter
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "customer_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name="active")
    private boolean active;
}
