package com.perficient.shoppingcart.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represent a item from the cart
 */
@AllArgsConstructor
@Getter
public class CartItemDomain {
    /**
     * The item quantity
     */
    private Integer quantity;

    /**
     * The item unit price
     */
    private BigDecimal unitPrice;
}
