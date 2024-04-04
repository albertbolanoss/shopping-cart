package com.perficient.shoppingcart.domain.model;

import com.perficient.shoppingcart.domain.services.PaymentSummaryService;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.PaymentSummaryDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@AllArgsConstructor
public class Cart {
    private final List<CartItemDomain> cartItemsDomain;

    private final PaymentSummaryService paymentSummaryService;

    /**
     * Calculate the payment summary and the total with fee
     * @return the payment summary
     */
    public PaymentSummaryDomain getPaymentSummaryAndTotalWithFee() {
        var subtotal = Optional.ofNullable(cartItemsDomain)
                .orElse(new ArrayList<>())
                .stream()
                .map(cartItem -> cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var total = paymentSummaryService.calculateTotalWithFee(subtotal);

        return new PaymentSummaryDomain(total, cartItemsDomain);
    }

}
