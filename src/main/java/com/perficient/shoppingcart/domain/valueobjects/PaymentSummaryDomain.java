package com.perficient.shoppingcart.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class PaymentSummaryDomain {
    /**
     * The total
     */
    private BigDecimal total;

    /**
     * The cart items domain
     */
    private List<CartItemDomain> cartItemDomainList;
}
