package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.exceptions.CartEmptyException;
import com.perficient.shoppingcart.domain.model.PaymentMethod;
import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import com.perficient.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.perficient.shoppingcart.infrastructure.mother.PaymentSummaryDomainMother;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartItemsCheckoutAppTest {
    @InjectMocks
    private CartItemsCheckoutApp cartItemsCheckoutApp;

    @Mock
    private GetPaymentSummaryApp getPaymentSummaryApp;

    @Mock
    private ProductDomainRepository productDomainRepository;

    @Test
    void checkoutWithVisa() {
        var paymentMethod = PaymentMethod.VISA;
        var cartItemsDomain = List.of(
                CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random());
        var expectedPaymentSummary = PaymentSummaryDomainMother.random();

        when(getPaymentSummaryApp.getPaymentSummary(any(PaymentMethod.class), any()))
                .thenReturn(expectedPaymentSummary);

        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class)))
                .thenReturn(200)
                .thenReturn(200)
                .thenReturn(200);

        doNothing().when(productDomainRepository).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        var paymentSummary = cartItemsCheckoutApp.checkout(paymentMethod, cartItemsDomain);

        verify(productDomainRepository, times(3))
                .updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        assertNotNull(paymentSummary);
        assertNotNull(paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomain());
        assertFalse(paymentSummary.getCartItemDomain().isEmpty());
    }

    @Test
    void checkoutWithMasterCard() {
        var paymentMethod = PaymentMethod.MASTERCARD;
        var cartItemsDomain = List.of(
                CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random());
        var expectedPaymentSummary = PaymentSummaryDomainMother.random();

        when(getPaymentSummaryApp.getPaymentSummary(any(PaymentMethod.class), any()))
                .thenReturn(expectedPaymentSummary);

        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class)))
                .thenReturn(200)
                .thenReturn(200)
                .thenReturn(200);

        doNothing().when(productDomainRepository).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        var paymentSummary = cartItemsCheckoutApp.checkout(paymentMethod, cartItemsDomain);

        verify(productDomainRepository, times(3))
                .updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        assertNotNull(paymentSummary);
        assertNotNull(paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomain());
        assertFalse(paymentSummary.getCartItemDomain().isEmpty());
    }

    @Test
    void checkoutWithCash() {
        var paymentMethod = PaymentMethod.CASH;
        var cartItemsDomain = List.of(
                CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random());
        var expectedPaymentSummary = PaymentSummaryDomainMother.random();

        when(getPaymentSummaryApp.getPaymentSummary(any(PaymentMethod.class), any()))
                .thenReturn(expectedPaymentSummary);

        when(productDomainRepository.getStockQuantity(any(ProductIdDomain.class)))
                .thenReturn(200)
                .thenReturn(200)
                .thenReturn(200);

        doNothing().when(productDomainRepository).updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        var paymentSummary = cartItemsCheckoutApp.checkout(paymentMethod, cartItemsDomain);

        verify(productDomainRepository, times(3))
                .updateStockQuantity(any(ProductIdDomain.class), any(Integer.class));

        assertNotNull(paymentSummary);
        assertNotNull(paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomain());
        assertFalse(paymentSummary.getCartItemDomain().isEmpty());
    }

    @Test
    void checkoutWithEmptyCart() {
        var paymentMethod = PaymentMethod.VISA;
        List<CartItemDomain> cartItemsDomain = new ArrayList<>();

        assertThrows(CartEmptyException.class, () -> cartItemsCheckoutApp.checkout(paymentMethod, cartItemsDomain));
    }

}
