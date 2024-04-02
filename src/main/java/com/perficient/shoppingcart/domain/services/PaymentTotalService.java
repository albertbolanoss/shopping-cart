package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.enumerators.PaymentMethod;
import com.perficient.shoppingcart.domain.exceptions.PaymentMethodNotSupportedException;
import com.perficient.shoppingcart.domain.services.PaymentTotal;
import com.perficient.shoppingcart.domain.services.PaymentTotalCashService;
import com.perficient.shoppingcart.domain.services.PaymentTotalMaterCardService;
import com.perficient.shoppingcart.domain.services.PaymentTotalVisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Payment total strategy service
 */
@Service
public class PaymentTotalService {
    private final Map<String, PaymentTotal> paymentTotalMap;

    @Autowired
    public PaymentTotalService(PaymentTotalVisaService paymentTotalVisaService,
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
     * @param subtotal the subtotal
     * @return a big decimal of the total with fee
     */
    BigDecimal calculateTotalWithFee(PaymentMethod paymentMethod, BigDecimal subtotal) {
        PaymentTotal paymentTotal = Optional.ofNullable(paymentTotalMap.get(paymentMethod.name()))
                .orElseThrow(() -> new PaymentMethodNotSupportedException("The payment method is not supported yet"));
        return paymentTotal.calculateTotalWithFee(subtotal);
    }
}
