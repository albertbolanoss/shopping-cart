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

    private Integer quantity;

    private BigDecimal unitPrice;
}
