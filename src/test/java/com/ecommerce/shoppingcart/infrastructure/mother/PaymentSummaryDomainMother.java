package com.ecommerce.shoppingcart.infrastructure.mother;

import com.ecommerce.shared.infrastructure.mother.FakerMother;
import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.PaymentSummaryDomain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Generate Payment summary domain instances with random data
 */
public class PaymentSummaryDomainMother {
    /**
     * Generate a random payment summary domain
     * @return a payment summary domain
     */
    public static PaymentSummaryDomain random() {
        List<CartItemDomain> cartItemsDomain = Arrays.asList(
                CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random()
        );
        return new PaymentSummaryDomain(
                BigDecimal.valueOf(FakerMother.getFaker().random().nextDouble(10000D, 50000D)),
                cartItemsDomain
        );
    }
}
