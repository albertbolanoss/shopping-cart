package com.perficient.shoppingcart.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "CUSTOMER")
@AllArgsConstructor
@Getter
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "CUSTOMER_ID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name="ACTIVE")
    private boolean active;
}
