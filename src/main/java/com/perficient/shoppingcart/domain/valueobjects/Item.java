package com.perficient.shoppingcart.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Item {
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
}
