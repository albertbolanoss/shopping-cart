package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.enumerators.PaymentMethod;
import com.perficient.shoppingcart.domain.exceptions.CartEmptyException;
import com.perficient.shoppingcart.domain.exceptions.NotExistException;
import com.perficient.shoppingcart.domain.exceptions.ProductNotAvailableException;
import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.PaymentSummaryDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Product service domain
 */
@Service
public class CartService {
    /**
     * Product domain repository
     */
    private final ProductDomainRepository productDomainRepository;

    /**
     * Payment total services
     */
    private final CartPaymentService paymentTotalService;

    @Autowired
    public CartService(ProductDomainRepository productDomainRepository,
                       CartPaymentService paymentTotalService) {
        this.productDomainRepository = productDomainRepository;
        this.paymentTotalService = paymentTotalService;
    }

    /**
     * Get a product items from the stock
     * @param productIdDomain the product id domain
     */
    @Transactional
    public void addItemToCart(ProductIdDomain productIdDomain,
                                        ConcurrentMap<String, CartItemDomain> cart) {

        cart = Optional.ofNullable(cart).orElseGet(ConcurrentHashMap::new);

        var productDomain = productDomainRepository.getProductById(productIdDomain)
                .orElseThrow(() -> new NotExistException(
                        String.format("The product (%s) not exist", productIdDomain.getId())));
        var currentQuantityInStock = productDomainRepository.getStockQuantity(productIdDomain);

        if (currentQuantityInStock <= 0) {
            throw new ProductNotAvailableException(
                    String.format("The product (%s) is not available in the stock", productIdDomain.getId()));
        }

        var cartItemDomain = Optional.ofNullable(cart.get(productIdDomain.getId()))
            .map(item -> new CartItemDomain(
                    item.getQuantity() + 1,
                    item.getUnitPrice(),
                    item.getProductIdDomain())
            ).orElse(new CartItemDomain(
                1,
                productDomain.getUnitPrice(),
                productDomain.getProductIdDomain())
            );

        var newQuantityInStock = currentQuantityInStock - 1;
        productDomainRepository.updateStockQuantity(productIdDomain, newQuantityInStock);

        cart.put(productIdDomain.getId(), cartItemDomain);
    }

    /**
     * Delete an item from cart
     * @param productIdDomain the product id domain
     * @param cartItemsDomain the cart items domain
     */
    @Transactional
    public void deleteItemFromCart(ProductIdDomain productIdDomain,
                                           ConcurrentMap<String, CartItemDomain> cartItemsDomain) {

        checkIfCartIsEmpty(cartItemsDomain);

        var productId = productIdDomain.getId();
        var cartItem = Optional.ofNullable(cartItemsDomain.get(productId))
                .orElseThrow(() -> new NotExistException("The product not exist in the cart"));

        if (cartItem.getQuantity() > 1) {
            var quantity = cartItem.getQuantity() - 1;
            var item = new CartItemDomain(quantity, cartItem.getUnitPrice(), cartItem.getProductIdDomain());

            cartItemsDomain.put(productId, item);
        } else {
            cartItemsDomain.remove(productId);
        }

        var newQuantityInStock = productDomainRepository.getStockQuantity(productIdDomain) + 1;
        productDomainRepository.updateStockQuantity(productIdDomain, newQuantityInStock);
    }

    /**
     * Delete all the item from cart
     * @param cartItemsDomain the cart items domain
     */
    @Transactional
    public void deleteAllItemsFromCart(ConcurrentMap<String, CartItemDomain> cartItemsDomain) {
        checkIfCartIsEmpty(cartItemsDomain);

        HashMap<String, Integer> stockItems = new HashMap<>();
        cartItemsDomain.keySet().forEach(productId -> {
            var productIdDomain = new ProductIdDomain(productId);
            var cartItem = cartItemsDomain.get(productId);
            var newQuantityInStock = productDomainRepository.getStockQuantity(productIdDomain) + cartItem.getQuantity();
            stockItems.put(cartItem.getProductIdDomain().getId(), newQuantityInStock);
        });

        stockItems.keySet().forEach(productId -> {
            Integer newQuantityInStock = stockItems.get(productId);
            productDomainRepository.updateStockQuantity(new ProductIdDomain(productId), newQuantityInStock);
        });

        cartItemsDomain.clear();
    }

    public PaymentSummaryDomain getPaymentSummary(PaymentMethod paymentMethod,
                                                  ConcurrentMap<String, CartItemDomain> cartItemsDomain) {

        var total = paymentTotalService.calculateTotalWithFee(paymentMethod, cartItemsDomain);

        return new PaymentSummaryDomain(total, new ArrayList<>());
    }

    /**
     * Verify if the cart is empty
     * @param cart the cart to verify
     */
    private void checkIfCartIsEmpty(ConcurrentMap<String, CartItemDomain> cart) {
        if (cart  == null || cart.isEmpty()) {
            throw new CartEmptyException("The operation is not valid because The cart is empty");
        }
    }

}
