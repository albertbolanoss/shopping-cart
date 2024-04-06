package com.ecommerce.shoppingcart.infrastructure.mother;

import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;

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
                FakerMother.getFaker().number().numberBetween(1,100),
                BigDecimal.valueOf(FakerMother.getFaker().number().randomDouble(6, 100L, 5000L)),
                ProductIdDomainMother.random()

        );
    }

    /**
     * Generate a random cart item domain
     * @return a cart item domain
     */
    public static CartItemDomain twoOrMoreInStock() {
        return new CartItemDomain(
                FakerMother.getFaker().number().numberBetween(2,50),
                BigDecimal.valueOf(FakerMother.getFaker().number().randomDouble(6, 100L, 5000L)),
                ProductIdDomainMother.random()
        );
    }

    /**
     * Generate a random cart item domain
     * @return a cart item domain
     */
    public static CartItemDomain onlyOneInStocks() {
        return new CartItemDomain(
                1,
                BigDecimal.valueOf(FakerMother.getFaker().number().randomDouble(6, 100L, 5000L)),
                ProductIdDomainMother.random()
        );
    }

    /**
     * Generate a random cart item domain
     * @return a cart item domain
     */
    public static CartItemDomain withFixValues() {
        return new CartItemDomain(
                5,
                new BigDecimal("18542.5645475"),
                ProductIdDomainMother.random()
        );
    }
}
