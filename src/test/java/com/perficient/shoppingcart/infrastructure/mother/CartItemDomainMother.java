package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;

import java.math.BigDecimal;

/**
 * Convert to Cart Item domain
 */
public class CartItemDomainMother {
    /**
     * Generate a random cart item domain
     * @return a cart item domain
     */
    public static CartItemDomain random() {
        return new CartItemDomain(
                FakerMother.faker.number().numberBetween(1,100),
                BigDecimal.valueOf(FakerMother.faker.number().randomDouble(6, 100L, 5000L))
        );
    }

    /**
     * Generate a random cart item domain
     * @return a cart item domain
     */
    public static CartItemDomain twoOrMoreInStock() {
        return new CartItemDomain(
                FakerMother.faker.number().numberBetween(2,50),
                BigDecimal.valueOf(FakerMother.faker.number().randomDouble(6, 100L, 5000L))
        );
    }

    /**
     * Generate a random cart item domain
     * @return a cart item domain
     */
    public static CartItemDomain onInStocks() {
        return new CartItemDomain(
                1,
                BigDecimal.valueOf(FakerMother.faker.number().randomDouble(6, 100L, 5000L))
        );
    }

    /**
     * Generate a random cart item domain
     * @return a cart item domain
     */
    public static CartItemDomain withFixValues() {
        return new CartItemDomain(
                5,
                new BigDecimal("18542.5645475")
        );
    }
}
