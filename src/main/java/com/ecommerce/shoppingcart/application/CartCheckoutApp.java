package com.ecommerce.shoppingcart.application;

import com.ecommerce.shoppingcart.domain.exceptions.CartEmptyException;
import com.ecommerce.shoppingcart.domain.model.PaymentMethod;
import com.ecommerce.shoppingcart.domain.repositories.ProductDomainRepository;
import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.PaymentSummaryDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class CartCheckoutApp {
    /**
     * The get payment summary service
     */
    private final GetPaymentSummaryApp getPaymentSummaryApp;

    /**
     * The product domain repository
     */
    private final ProductDomainRepository productDomainRepository;

    @Autowired
    public CartCheckoutApp(GetPaymentSummaryApp getPaymentSummaryApp,
                           ProductDomainRepository productDomainRepository) {
        this.getPaymentSummaryApp = getPaymentSummaryApp;
        this.productDomainRepository = productDomainRepository;
    }

    /**
     * Get the payment summary and calculate the total to pay
     * @param paymentMethod the payment method
     * @param cartItemsDomain the cart items domain
     * @return a payment summary domain
     */
    public PaymentSummaryDomain checkout(PaymentMethod paymentMethod, List<CartItemDomain> cartItemsDomain) {
        if (cartItemsDomain == null || cartItemsDomain.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "The operation is not valid because The cart is empty");
        }

        var paymentSummaryDomain =  getPaymentSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain);
        var paymentSummaryItems = paymentSummaryDomain.getCartItemDomain();

        try {
            productDomainRepository.updateStockQuantity(paymentSummaryItems);
        } catch (CartEmptyException ex) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

        return paymentSummaryDomain;
    }
}
