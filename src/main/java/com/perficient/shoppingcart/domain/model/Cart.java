package com.perficient.shoppingcart.domain.model;

import com.perficient.shoppingcart.domain.exceptions.CartEmptyException;
import com.perficient.shoppingcart.domain.services.PaymentSummaryService;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.PaymentSummaryDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RequiredArgsConstructor
public class Cart {
    private final List<CartItemDomain> cartItemsDomain;

    private PaymentSummaryService paymentSummaryService;

    /**
     * Find a item filter by product id
     * @param productIdDomain the product id domain
     * @return a optional of the cart item domain
     */
    public Optional<CartItemDomain> findItemInCart(ProductIdDomain productIdDomain) {
        var items = Optional.ofNullable(cartItemsDomain).orElseGet(ArrayList::new);
        return items.stream()
                .filter(item -> item.getProductIdDomain().getId()
                        .equals(productIdDomain.getId())).findFirst();
    }

    /**
     * Calculate the payment summary and the total with fee
     * @return the payment summary
     */
    public PaymentSummaryDomain getPaymentSummaryAndTotalWithFee() {
        var subtotal = Optional.ofNullable(cartItemsDomain)
                .orElse(new ArrayList<>())
                .stream()
                .map(cartItem -> cartItem.getUnitPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var total = paymentSummaryService.calculateTotalWithFee(subtotal);

        return new PaymentSummaryDomain(total, cartItemsDomain);
    }

    /**
     * Add a item quantity or create a new item
     * @param productDomain the product domain
     * @return a Cart Item Domain
     */
    public CartItemDomain addItem(ProductDomain productDomain) {
        var currentCartItemDomain = findItemInCart(productDomain.getProductIdDomain())
                .orElseGet(() -> new CartItemDomain(
                        0,
                        productDomain.getUnitPrice(),
                        productDomain.getProductIdDomain())
                );


        return new CartItemDomain(
                currentCartItemDomain.getQuantity() + 1,
                currentCartItemDomain.getUnitPrice(),
                currentCartItemDomain.getProductIdDomain());

    }

    /**
     * Remove an item
     * @param productIdDomain the product id domain
     * @return a list of the items
     */
    public Optional<CartItemDomain> removeItem(ProductIdDomain productIdDomain) {
        var currentItem =  findItemInCart(productIdDomain)
                .orElseThrow(() -> new CartEmptyException(
                        "This operation cannot be completed because the cart is empty"
                ));

        return Optional.of(currentItem).map(item -> {
            if (item.getQuantity() > 1) {
                return new CartItemDomain(
                        item.getQuantity() - 1,
                        item.getUnitPrice(),
                        item.getProductIdDomain());
            }

            return null;
        });


    }


}
