package com.perficient.shoppingcart.domain.valueobjects;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Represent a item from the cart
 */
@AllArgsConstructor
@Getter
public class CartItemDomain {
    @Size(max = 36)
    private String id;

    private Integer quantity;

    private BigDecimal unitPrice;
}
