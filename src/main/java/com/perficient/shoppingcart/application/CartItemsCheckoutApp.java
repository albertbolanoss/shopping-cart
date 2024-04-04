package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.exceptions.CartEmptyException;
import com.perficient.shoppingcart.domain.model.PaymentMethod;
import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.PaymentSummaryDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsCheckoutApp {
    /**
     * The get payment summary service
     */
    private final GetPaymentSummaryApp getPaymentSummaryApp;

    /**
     * The product domain repository
     */
    private final ProductDomainRepository productDomainRepository;

    @Autowired
    public CartItemsCheckoutApp(GetPaymentSummaryApp getPaymentSummaryApp,
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
            throw new CartEmptyException("The operation is not valid because The cart is empty");
        }
        var paymentSummaryDomain =  getPaymentSummaryApp.getPaymentSummary(paymentMethod, cartItemsDomain);

        paymentSummaryDomain.getCartItemDomain().stream().map(cartItemDomain -> {
            var productIdDomain = cartItemDomain.getProductIdDomain();
            var currentQuantity =  productDomainRepository.getStockQuantity(productIdDomain);
            var newStockQuantity =  currentQuantity - cartItemDomain.getQuantity();

            return  new CartItemDomain(newStockQuantity, cartItemDomain.getUnitPrice(), productIdDomain);
        }).forEach(cartItemDomain -> productDomainRepository.updateStockQuantity(
                cartItemDomain.getProductIdDomain(), cartItemDomain.getQuantity()));

        return paymentSummaryDomain;
    }
}
