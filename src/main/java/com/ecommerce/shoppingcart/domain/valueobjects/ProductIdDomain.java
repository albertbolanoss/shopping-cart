package com.ecommerce.shoppingcart.domain.valueobjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represent a Product id domain
 */
@AllArgsConstructor
@Getter
public class ProductIdDomain {
    /**
     * The Customer Identified
     */
    private @Size(max = 36) @NotNull String id;
}
