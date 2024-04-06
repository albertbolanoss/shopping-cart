package com.ecommerce.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.application.api.model.ItemReq;
import com.ecommerce.shoppingcart.domain.valueobjects.CartItemDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.ProductIdDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

/**
 * Convert to Item from model api
 */
public class ItemModelApiMapper {

    /**
     * Convert the cart domain items to a list of items from API Model
     * @param CartItemDomain the cart items
     * @return a list of model api items
     */
    public static List<ItemReq> fromConcurrentMapCart(ConcurrentMap<String, CartItemDomain> CartItemDomain) {
        List<ItemReq> items = new ArrayList<>();

        if (Optional.ofNullable(CartItemDomain).isPresent()) {
            for (Map.Entry<String, CartItemDomain> entry : CartItemDomain.entrySet()) {
                String productId = entry.getKey();
                CartItemDomain cartItem = entry.getValue();

                ItemReq item = new ItemReq()
                        .id(productId)
                        .quantity(cartItem.getQuantity())
                        .unitPrice(cartItem.getUnitPrice());

                items.add(item);
            }
        }
        return items;
    }

    public static ItemReq fromDomain(CartItemDomain cartItemDomain) {
        return Optional.ofNullable(cartItemDomain)
                .map(domain -> new ItemReq()
                        .id(Optional.ofNullable(domain.getProductIdDomain()).map(ProductIdDomain::getId).orElse(null))
                        .unitPrice(domain.getUnitPrice())
                        .quantity(domain.getQuantity()))
                .orElse(null);
    }

}
