package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.model.Cart;
import com.perficient.shoppingcart.domain.model.PaymentMethod;
import com.perficient.shoppingcart.domain.services.CartPaymentService;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.PaymentSummaryDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Get payment summary service
 */
@Service
public class GetPaymentSummaryApp {
    private final CartPaymentService cartPaymentService;

    @Autowired
    public GetPaymentSummaryApp(CartPaymentService cartPaymentService) {
        this.cartPaymentService = cartPaymentService;
    }

    /**
     * Get the payment summary and calculate the total to pay
     * @param paymentMethod the payment method
     * @param cartItemsDomain the cart items domain
     * @return a payment summary domain
     */
    public PaymentSummaryDomain getPaymentSummary(PaymentMethod paymentMethod, List<CartItemDomain> cartItemsDomain) {
        var paymentSummaryService = cartPaymentService.getPaymentSummaryService(paymentMethod);
        var cartDomain =  new Cart(cartItemsDomain, paymentSummaryService);

        return cartDomain.getPaymentSummaryAndTotalWithFee();
    }
}
