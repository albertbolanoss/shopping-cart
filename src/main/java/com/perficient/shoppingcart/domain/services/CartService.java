package com.perficient.shoppingcart.domain.services;

import com.perficient.shoppingcart.domain.exceptions.CartEmptyException;
import com.perficient.shoppingcart.domain.exceptions.NotExistException;
import com.perficient.shoppingcart.domain.exceptions.ProductNotAvailableException;
import com.perficient.shoppingcart.domain.repositories.ProductDomainRepository;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;
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

    @Autowired
    public CartService(ProductDomainRepository productDomainRepository) {
        this.productDomainRepository = productDomainRepository;
    }

    /**
     * Get a product items from the stock
     * @param productIdDomain the product id domain
     * @return an updated cart item
     */
    public CartItemDomain getItemFromStock(ProductIdDomain productIdDomain,
                                           ConcurrentMap<String, CartItemDomain> cartItemsDomain) {
        var currentProductDomain = productDomainRepository.getProductFromStock(productIdDomain)
                .orElseThrow(() -> new NotExistException(
                    String.format("The product (%s) does not exist", productIdDomain.getId())));

        if (currentProductDomain.getStock() <= 0) {
            throw new ProductNotAvailableException(
                    String.format("The product (%s) is not available in the stock", productIdDomain.getId()));
        }

        var productStock = currentProductDomain.getStock() - 1;
        updateProductInStock(currentProductDomain, productStock);

        var currentQuantity = Optional.ofNullable(cartItemsDomain.get(productIdDomain.getId()))
            .map(CartItemDomain::getQuantity)
            .orElse(0);

        return new CartItemDomain(
                currentQuantity + 1,
                currentProductDomain.getUnitPrice()
        );
    }

    /**
     * Delete an item from cart
     * @param productIdDomain the product id domain
     * @param cartItemsDomain the cart items domain
     * @return the ConcurrentMap cart item domain
     */
    public ConcurrentMap<String, CartItemDomain> deleteItemFromCart(ProductIdDomain productIdDomain,
                                           ConcurrentMap<String, CartItemDomain> cartItemsDomain) {

        if (cartItemsDomain == null || cartItemsDomain.isEmpty()) {
            throw new CartEmptyException("The cart does not contain any item");
        }

        ConcurrentMap<String, CartItemDomain> cart = new ConcurrentHashMap<>(cartItemsDomain);

        var currentProductDomain = productDomainRepository.getProductFromStock(productIdDomain)
                .orElseThrow(() -> new NotExistException(
                        String.format("The product (%s) does not exist", productIdDomain.getId())));

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
        if (cartItemsDomain == null || cartItemsDomain.isEmpty()) {
            throw new CartEmptyException("The cart does not contain any item");
        }

        ConcurrentMap<String, CartItemDomain> cart = new ConcurrentHashMap<>(cartItemsDomain);
        List<ProductDomain> products = new ArrayList<>();

        cart.keySet().forEach(productId -> {
            var productIdDomain = new ProductIdDomain(productId);
            var cartItem = cart.get(productId);
            var currentProductDomain = productDomainRepository.getProductFromStock(productIdDomain)
                    .orElseThrow(() -> new NotExistException(
                            String.format("The product (%s) does not exist", productIdDomain.getId())));

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
