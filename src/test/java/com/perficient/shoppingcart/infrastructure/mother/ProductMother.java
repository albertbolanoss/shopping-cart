package com.perficient.shoppingcart.infrastructure.mother;

import com.perficient.shoppingcart.infrastructure.entities.Product;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Generate random data for Product entity
 */
public class ProductMother {
    /**
     * Generate a random and active entity product
     * @return a entity product
     */
    public static Product random() {
        return new Product(
            UUID.randomUUID().toString(),
            FakerMother.getFaker().commerce().productName(),
            FakerMother.getFaker().commerce().promotionCode(),
            FakerMother.getFaker().commerce().material(),
            new BigDecimal(FakerMother.getFaker().commerce().price()),
            FakerMother.getFaker().random().nextInt(),
            Boolean.TRUE
        );
    }
}
