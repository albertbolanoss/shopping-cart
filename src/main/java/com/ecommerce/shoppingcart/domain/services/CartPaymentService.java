package com.ecommerce.shoppingcart.domain.services;

import com.ecommerce.shoppingcart.domain.model.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Payment total strategy service
 */
@Service
public class CartPaymentService {
    private final Map<String, PaymentSummaryService> paymentTotalMap;

    @Autowired
    public CartPaymentService(PaymentSummaryVisaService paymentTotalVisaService,
                              PaymentSummaryMaterCardService paymentTotalMaterCardService,
                              PaymentSummaryCashService paymentTotalCashService) {
        this.paymentTotalMap = new HashMap<>();
        paymentTotalMap.put(PaymentMethod.VISA.name(), paymentTotalVisaService);
        paymentTotalMap.put(PaymentMethod.MASTERCARD.name(), paymentTotalMaterCardService);
        paymentTotalMap.put(PaymentMethod.CASH.name(), paymentTotalCashService);
    }

    /**
     * Get the payment summary service
     * @param paymentMethod the payment method
     * @return a service of PaymentSummaryService according to payment method
     */
    public PaymentSummaryService getPaymentSummaryService(PaymentMethod paymentMethod) {
        return paymentTotalMap.get(paymentMethod.name());
    }

}
