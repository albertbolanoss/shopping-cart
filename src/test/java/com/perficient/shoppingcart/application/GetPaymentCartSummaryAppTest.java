package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.enumerators.PaymentMethod;
import com.perficient.shoppingcart.domain.exceptions.PaymentMethodNotSupportedException;
import com.perficient.shoppingcart.domain.services.CartPaymentService;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.PaymentSummaryDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.ProductIdDomainMother;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetPaymentCartSummaryAppTest {
    @InjectMocks
    private GetPaymentCartSummaryApp getPaymentCartSummaryApp;
    @Mock
    private CartPaymentService cartPaymentService;
    @Test
    void getPaymentSummaryWhenIsSuccessfully() {
        var paymentMethod = PaymentMethod.VISA;
        var productIdDomain = ProductIdDomainMother.random();
        var cartItemDomain = CartItemDomainMother.random();
        var expectedPaymentSummary = PaymentSummaryDomainMother.random();

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productIdDomain.getId(), cartItemDomain);

        when(cartPaymentService.calculateTotalWithFee(any(PaymentMethod.class), any()))
                .thenReturn(expectedPaymentSummary);

        var paymentSummary = getPaymentCartSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain);

        verify(cartPaymentService, atLeastOnce()).calculateTotalWithFee(any(PaymentMethod.class), any());
        assertNotNull(paymentSummary);
        assertNotNull(paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomainList());
        assertFalse(paymentSummary.getCartItemDomainList().isEmpty());
    }

    @Test
    void getPaymentSummaryWhenPaymentMethodIsNotValid() {
        var paymentMethod = PaymentMethod.CASH;
        var productIdDomain = ProductIdDomainMother.random();
        var cartItemDomain = CartItemDomainMother.random();

        ConcurrentMap<String, CartItemDomain> cartItemsDomain = new ConcurrentHashMap<>();
        cartItemsDomain.put(productIdDomain.getId(), cartItemDomain);

        when(cartPaymentService.calculateTotalWithFee(any(PaymentMethod.class), any()))
                .thenThrow(new PaymentMethodNotSupportedException("The payment method is not supported yet"));

        assertThrows(HttpClientErrorException.class,
                () -> getPaymentCartSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain));

    }
}
