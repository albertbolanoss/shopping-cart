package com.perficient.shoppingcart.domain.valueobjects;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represent a domain product
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductDomain {
    /**
     * The product id
     */
    private ProductIdDomain productIdDomain;

    /**
     * Product code
     */
    @Size(max = 100)
    private String code;

    /**
     * The product name
     */
    @Size(max = 150)
    private String name;
    /**
     * The product unit price
     */
    private BigDecimal unitPrice;
    /**
     * The quantity of item of the product in stock
     */
    private int stock;
    /**
     * if the product is active or disabled
     */
    private Boolean active;
}
