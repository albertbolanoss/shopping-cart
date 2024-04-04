package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.application.api.model.PaymentSummaryReq;
import com.perficient.shoppingcart.domain.valueobjects.PaymentSummaryDomain;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Convert to Payment Summary Req model API
 */
public class PaymentSummaryReqMapper {
    /**
     * Convert Payment Summary Domain to Payment Summary Req from model API
     * @param paymentSummaryDomain the payment summary domain
     * @return a Payment Summary Req from model API
     */
    public static PaymentSummaryReq fromDomain(PaymentSummaryDomain paymentSummaryDomain) {
        return Optional.ofNullable(paymentSummaryDomain)
                .map(domain -> new PaymentSummaryReq()
                        .total(domain.getTotal())
                        .items(Optional.ofNullable(paymentSummaryDomain.getCartItemDomainList())
                                .orElse(new ArrayList<>())
                                .stream().map(ItemModelApiMapper::fromDomain)
                                .collect(Collectors.toList())
                        )
                )
                .orElse(null);

    }
}
