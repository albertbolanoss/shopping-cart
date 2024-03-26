package com.perficient.shoppingcart.domain.valueobjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represent a Customer id domain
 */
@AllArgsConstructor
@Getter
public class CustomerId {
    /**
     * The Customer Identified
     */
    private final @NotNull @Size(max = 36) String id;
}
