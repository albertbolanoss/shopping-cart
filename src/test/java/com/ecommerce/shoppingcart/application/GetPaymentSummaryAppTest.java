package com.ecommerce.shoppingcart.application;

import com.ecommerce.shoppingcart.domain.model.PaymentMethod;
import com.ecommerce.shoppingcart.domain.services.CartPaymentService;
import com.ecommerce.shoppingcart.domain.services.PaymentSummaryCashService;
import com.ecommerce.shoppingcart.domain.services.PaymentSummaryMaterCardService;
import com.ecommerce.shoppingcart.domain.services.PaymentSummaryService;
import com.ecommerce.shoppingcart.domain.services.PaymentSummaryVisaService;
import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.infrastructure.mother.CartItemDomainMother;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetPaymentSummaryAppTest {
    @InjectMocks
    private GetPaymentSummaryApp getPaymentSummaryApp;
    @Mock
    private CartPaymentService cartPaymentService;

    private Map<String, PaymentSummaryService> paymentSummaryService;


    @Test
    void getPaymentSummaryWithVisa() {
        var paymentMethod = PaymentMethod.VISA;
        var cartItemsDomain = List.of(
                CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random());

        when(cartPaymentService.getPaymentSummaryService(any(PaymentMethod.class)))
                .thenReturn(new PaymentSummaryVisaService());

        var paymentSummary = getPaymentSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain);

        assertNotNull(paymentSummary);
        assertNotNull(paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomain());
        assertFalse(paymentSummary.getCartItemDomain().isEmpty());
    }

    @Test
    void getPaymentSummaryWithMastercard() {
        var paymentMethod = PaymentMethod.MASTERCARD;
        var cartItemsDomain = List.of(
                CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random());

        when(cartPaymentService.getPaymentSummaryService(any(PaymentMethod.class)))
                .thenReturn(new PaymentSummaryMaterCardService());

        var paymentSummary = getPaymentSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain);

        assertNotNull(paymentSummary);
        assertNotNull(paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomain());
        assertFalse(paymentSummary.getCartItemDomain().isEmpty());
    }


    @Test
    void getPaymentSummaryWithCash() {
        var paymentMethod = PaymentMethod.CASH;
        var cartItemsDomain = List.of(
                CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random());

        when(cartPaymentService.getPaymentSummaryService(any(PaymentMethod.class)))
                .thenReturn(new PaymentSummaryCashService());

        var paymentSummary = getPaymentSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain);

        assertNotNull(paymentSummary);
        assertNotNull(paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomain());
        assertFalse(paymentSummary.getCartItemDomain().isEmpty());
    }

    @Test
    void getPaymentSummaryWithEmptyCartWithVisa() {
        var paymentMethod = PaymentMethod.VISA;
        List<CartItemDomain> cartItemsDomain = new ArrayList<>();

        when(cartPaymentService.getPaymentSummaryService(any(PaymentMethod.class)))
                .thenReturn(new PaymentSummaryVisaService());

        var paymentSummary = getPaymentSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain);

        assertNotNull(paymentSummary);
        assertEquals(new BigDecimal(0), paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomain());
        assertTrue(paymentSummary.getCartItemDomain().isEmpty());
    }

    @Test
    void getPaymentSummaryWithEmptyCartWithMasterCard() {
        var paymentMethod = PaymentMethod.MASTERCARD;
        List<CartItemDomain> cartItemsDomain = new ArrayList<>();

        when(cartPaymentService.getPaymentSummaryService(any(PaymentMethod.class)))
                .thenReturn(new PaymentSummaryVisaService());

        var paymentSummary = getPaymentSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain);

        assertNotNull(paymentSummary);
        assertEquals(new BigDecimal(0), paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomain());
        assertTrue(paymentSummary.getCartItemDomain().isEmpty());
    }

    @Test
    void getPaymentSummaryWithEmptyCartWithCash() {
        var paymentMethod = PaymentMethod.CASH;
        List<CartItemDomain> cartItemsDomain = new ArrayList<>();

        when(cartPaymentService.getPaymentSummaryService(any(PaymentMethod.class)))
                .thenReturn(new PaymentSummaryVisaService());

        var paymentSummary = getPaymentSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain);

        assertNotNull(paymentSummary);
        assertEquals(new BigDecimal(0), paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomain());
        assertTrue(paymentSummary.getCartItemDomain().isEmpty());
    }
}
