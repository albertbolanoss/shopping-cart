package com.ecommerce.shoppingcart.application;

import com.ecommerce.shoppingcart.domain.exceptions.NotAvailableInStockException;
import com.ecommerce.shoppingcart.domain.model.PaymentMethod;
import com.ecommerce.shoppingcart.domain.repositories.ProductDomainRepository;
import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.infrastructure.mother.CartItemDomainMother;
import com.ecommerce.shoppingcart.infrastructure.mother.PaymentSummaryDomainMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemsCheckoutAppTest {
    private CartCheckoutApp cartCheckoutApp;

    @Mock
    private GetPaymentSummaryApp getPaymentSummaryApp;

    @Mock
    private ProductDomainRepository productDomainRepository;

    @BeforeEach
    void init() {
        cartCheckoutApp = new CartCheckoutApp(getPaymentSummaryApp, productDomainRepository);
    }

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

        doNothing().when(productDomainRepository).updateStockQuantity(any());

        var paymentSummary = cartCheckoutApp.checkout(paymentMethod, cartItemsDomain);

        verify(productDomainRepository, atLeastOnce()).updateStockQuantity(any());

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

        doNothing().when(productDomainRepository).updateStockQuantity(any());

        var paymentSummary = cartCheckoutApp.checkout(paymentMethod, cartItemsDomain);

        verify(productDomainRepository, atLeastOnce()).updateStockQuantity(any());

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

        doNothing().when(productDomainRepository).updateStockQuantity(any());

        var paymentSummary = cartCheckoutApp.checkout(paymentMethod, cartItemsDomain);

        verify(productDomainRepository, atLeastOnce()).updateStockQuantity(any());

        assertNotNull(paymentSummary);
        assertNotNull(paymentSummary.getTotal());
        assertNotNull(paymentSummary.getCartItemDomain());
        assertFalse(paymentSummary.getCartItemDomain().isEmpty());
    }

    @Test
    void checkoutWithEmptyCart() {
        var paymentMethod = PaymentMethod.VISA;
        List<CartItemDomain> cartItemsDomain = new ArrayList<>();

        assertThrows(HttpClientErrorException.class, () -> cartCheckoutApp.checkout(paymentMethod, cartItemsDomain));
    }

    @Test
    void checkoutNotAvailableInStock() {
        var paymentMethod = PaymentMethod.MASTERCARD;
        var cartItemsDomain = List.of(
                CartItemDomainMother.random(),
                CartItemDomainMother.random(),
                CartItemDomainMother.random());
        var expectedPaymentSummary = PaymentSummaryDomainMother.random();
        var objectError = new ObjectError("field", "mensaje de error");
        var allErrors = List.of(objectError);

        when(getPaymentSummaryApp.getPaymentSummary(any(PaymentMethod.class), any()))
                .thenReturn(expectedPaymentSummary);

        doThrow(new NotAvailableInStockException("NotAvailableInStockException", allErrors))
                .when(productDomainRepository).updateStockQuantity(any());

        assertThrows(NotAvailableInStockException.class, () -> cartCheckoutApp.checkout(paymentMethod, cartItemsDomain));
    }

}
