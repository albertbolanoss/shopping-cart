package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;

import java.util.UUID;

/**
 * Generate random data for Product id domain
 */
public class ProductIdDomainMother {
    /**
     * Generate a random product id
     * @return a ProductIdDomain
     */
    public static ProductIdDomain random() {
        return new ProductIdDomain(UUID.randomUUID().toString());
    }
}
