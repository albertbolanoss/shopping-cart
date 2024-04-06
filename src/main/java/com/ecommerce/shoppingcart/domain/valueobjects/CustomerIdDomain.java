package com.ecommerce.shoppingcart.domain.valueobjects;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represent a Customer id domain
 */
@AllArgsConstructor
@Getter
public class CustomerIdDomain {
    /**
     * The Customer Identified
     */
    private final @Size(max = 36) String id;
}
