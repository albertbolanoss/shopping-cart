package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.enumerators.PaymentMethod;
import com.perficient.shoppingcart.domain.exceptions.CartEmptyException;
import com.perficient.shoppingcart.domain.exceptions.NotExistException;
import com.perficient.shoppingcart.domain.exceptions.ProductNotAvailableException;
import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
import com.perficient.shoppingcart.domain.valueobjects.PaymentSummaryDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductDomain;
import com.perficient.shoppingcart.domain.valueobjects.ProductIdDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
     * @return an updated cart item
     */
    public CartItemDomain getItemFromStock(ProductIdDomain productIdDomain,
                                           ConcurrentMap<String, CartItemDomain> cartItemsDomain) {
        var productDomain = getProductFromStock(productIdDomain);
        var currentQuantityInStock = productDomain.getStock();

        if (currentQuantityInStock <= 0) {
            throw new ProductNotAvailableException(
                    String.format("The product (%s) is not available in the stock", productIdDomain.getId()));
        }

        var newQuantityInStock = currentQuantityInStock - 1;
        var unitPrice = productDomain.getUnitPrice();
        var cartQuantityInCart = Optional.ofNullable(cartItemsDomain.get(productIdDomain.getId()))
            .map(item -> item.getQuantity() + 1)
            .orElse(1);

        updateProductInStock(productDomain, newQuantityInStock);

        return new CartItemDomain(cartQuantityInCart, unitPrice);
    }

    /**
     * Delete an item from cart
     * @param productIdDomain the product id domain
     * @param cartItemsDomain the cart items domain
     * @return the ConcurrentMap cart item domain
     */
    public ConcurrentMap<String, CartItemDomain> deleteItemFromCart(ProductIdDomain productIdDomain,
                                           ConcurrentMap<String, CartItemDomain> cartItemsDomain) {

        ConcurrentMap<String, CartItemDomain> cart = cloneCartItemDomain(cartItemsDomain);

        var currentProductDomain = getProductFromStock(productIdDomain);

        var productId = productIdDomain.getId();
        var cartItem = Optional.ofNullable(cart.get(productId))
                .orElseThrow(() -> new NotExistException("The product not exist in the cart"));

        if (cartItem.getQuantity() > 1) {
            var quantity = cartItem.getQuantity() - 1;
            var item = new CartItemDomain(quantity, cartItem.getUnitPrice());

            cart.put(productId, item);
        } else {
            cart.remove(productId);
        }

        var productStock = currentProductDomain.getStock() + 1;
        updateProductInStock(currentProductDomain, productStock);

        return cart;
    }

    /**
     * Delete all the item from cart
     * @param cartItemsDomain the cart items domain
     */
    public void deleteAllItemFromCart(ConcurrentMap<String, CartItemDomain> cartItemsDomain) {
        ConcurrentMap<String, CartItemDomain> cart = cloneCartItemDomain(cartItemsDomain);
        List<ProductDomain> products = new ArrayList<>();

        cart.keySet().forEach(productId -> {
            var productIdDomain = new ProductIdDomain(productId);
            var cartItem = cart.get(productId);
            var currentProductDomain = getProductFromStock(productIdDomain);

            var newProductStock = new ProductDomain(
                    currentProductDomain.getProductIdDomain(),
                    currentProductDomain.getCode(),
                    currentProductDomain.getName(),
                    currentProductDomain.getUnitPrice(),
                    currentProductDomain.getStock() + cartItem.getQuantity(),
                    currentProductDomain.getActive(),
                    currentProductDomain.getDescription()
            );
            products.add(newProductStock);
        });

        products.forEach(productDomainRepository::updateProductInStock);
    }

    public PaymentSummaryDomain getPaymentSummary(PaymentMethod paymentMethod,
                                                  ConcurrentMap<String, CartItemDomain> cartItemsDomain) {

        var total = paymentTotalService.calculateTotalWithFee(paymentMethod, cartItemsDomain);

        return new PaymentSummaryDomain(total, new ArrayList<>());
    }

    /**
     * Get the product from the stock
     * @param productIdDomain the product id domain
     * @return a product domain or throw aan exceptione
     */
    private ProductDomain getProductFromStock(ProductIdDomain productIdDomain) {
        return productDomainRepository.getProductFromStock(productIdDomain)
                .orElseThrow(() -> new NotExistException(
                        String.format("The product (%s) does not exist", productIdDomain.getId())));
    }

    /**
     * Clone the cart item domain or throw exception if the cart is empty
     * @param cartItemsDomain the cart item domain
     * @return a ConcurrentMap of cart item domain
     */
    private ConcurrentMap<String, CartItemDomain> cloneCartItemDomain(
            ConcurrentMap<String, CartItemDomain> cartItemsDomain) {
        if (cartItemsDomain == null || cartItemsDomain.isEmpty()) {
            throw new CartEmptyException("The cart does not contain any item");
        }

        return new ConcurrentHashMap<>(cartItemsDomain);
    }


    /**
     * Update the product stock
     * @param productDomain the product domain
     * @param quantity the product in stock
     */
    private void updateProductInStock(ProductDomain productDomain, int quantity) {
        var newProductStock = new ProductDomain(
                productDomain.getProductIdDomain(),
                productDomain.getCode(),
                productDomain.getName(),
                productDomain.getUnitPrice(),
                quantity,
                productDomain.getActive(),
                productDomain.getDescription()
        );
        productDomainRepository.updateProductInStock(newProductStock);
    }

}
