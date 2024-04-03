package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.enumerators.PaymentMethod;
import com.perficient.shoppingcart.domain.exceptions.PaymentMethodNotSupportedException;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.PaymentSummaryDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Payment total strategy service
 */
@Service
public class CartPaymentService {
    private final Map<String, PaymentTotal> paymentTotalMap;

    @Autowired
    public CartPaymentService(PaymentTotalVisaService paymentTotalVisaService,
                              PaymentTotalMaterCardService paymentTotalMaterCardService,
                              PaymentTotalCashService paymentTotalCashService) {
        this.paymentTotalMap = new HashMap<>();
        paymentTotalMap.put(PaymentMethod.VISA.name(), paymentTotalVisaService);
        paymentTotalMap.put(PaymentMethod.MASTERCARD.name(), paymentTotalMaterCardService);
        paymentTotalMap.put(PaymentMethod.CASH.name(), paymentTotalCashService);
    }

    /**
     *
     * @param paymentMethod the payment method enumerator
     * @param cart the cart items domain
     * @return the payment summary domain
     */
    PaymentSummaryDomain calculateTotalWithFee(PaymentMethod paymentMethod,
                                               ConcurrentMap<String, CartItemDomain> cart) {

        PaymentTotal paymentTotal = Optional.ofNullable(paymentTotalMap.get(paymentMethod.name()))
                .orElseThrow(() -> new PaymentMethodNotSupportedException("The payment method is not supported yet"));


        var cartItemsDomain = new ArrayList<>(Optional.ofNullable(cart)
                .orElse(new ConcurrentHashMap<>())
                .values());

        var subtotal = cartItemsDomain.stream()
                .map(cartItem -> cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var total = paymentTotal.calculateTotalWithFee(subtotal);

        return new PaymentSummaryDomain(total, cartItemsDomain);
    }
}
