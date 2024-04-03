package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.enumerators.PaymentMethod;
import com.perficient.shoppingcart.domain.exceptions.PaymentMethodNotSupportedException;
import com.perficient.shoppingcart.domain.services.CartPaymentService;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.PaymentSummaryDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.ConcurrentMap;

/**
 * The Get payment summary service
 */
@Service
public class GetPaymentCartSummaryApp {
    private final CartPaymentService cartPaymentService;

    @Autowired
    public GetPaymentCartSummaryApp(CartPaymentService cartPaymentService) {
        this.cartPaymentService = cartPaymentService;
    }

    /**
     * Get the payment summary and calculate the total to pay
     * @param paymentMethod the payment method
     * @param cart the cart items domain
     * @return a payment summary domain
     */
    public PaymentSummaryDomain getPaymentSummary(PaymentMethod paymentMethod,
                                          ConcurrentMap<String, CartItemDomain> cart) {
        try {
            return cartPaymentService.calculateTotalWithFee(paymentMethod, cart);
        } catch (PaymentMethodNotSupportedException ex) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
