package com.ecommerce.shoppingcart.domain.services;

import com.ecommerce.shoppingcart.domain.model.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CartPaymentServiceTest {
    private CartPaymentService cartPaymentTotalService;
    @Mock
    private PaymentSummaryVisaService paymentTotalVisaService;
    @Mock
    private PaymentSummaryMaterCardService paymentTotalMaterCardService;
    @Mock
    private PaymentSummaryCashService paymentTotalCashService;

    @BeforeEach
    void init() {
        cartPaymentTotalService = new CartPaymentService(paymentTotalVisaService,
                paymentTotalMaterCardService, paymentTotalCashService);
    }


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
