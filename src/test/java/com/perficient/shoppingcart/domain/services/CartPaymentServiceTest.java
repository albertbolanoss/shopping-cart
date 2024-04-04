package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.model.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CartPaymentServiceTest {
    @Autowired
    private CartPaymentService cartPaymentTotalService;

   @Test
    void getPaymentSummaryVisaService() {
       var paymentSummaryService = cartPaymentTotalService.getPaymentSummaryService(PaymentMethod.VISA);
       assertTrue(paymentSummaryService instanceof PaymentSummaryVisaService);
   }

    @Test
    void getPaymentSummaryMasterCardService() {
        var paymentSummaryService = cartPaymentTotalService.getPaymentSummaryService(PaymentMethod.MASTERCARD);
        assertTrue(paymentSummaryService instanceof PaymentSummaryMaterCardService);
    }

    @Test
    void getPaymentSummaryCashService() {
        var paymentSummaryService = cartPaymentTotalService.getPaymentSummaryService(PaymentMethod.CASH);
        assertTrue(paymentSummaryService instanceof PaymentSummaryCashService);
    }
}
