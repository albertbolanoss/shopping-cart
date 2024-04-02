package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.application.api.model.Item;
import com.perficient.shoppingcart.domain.valueobjects.CartItemDomain;

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
    public static List<Item> fromDomain(ConcurrentMap<String, CartItemDomain> CartItemDomain) {
        List<Item> items = new ArrayList<>();

        if (Optional.ofNullable(CartItemDomain).isPresent()) {
            for (Map.Entry<String, CartItemDomain> entry : CartItemDomain.entrySet()) {
                String productId = entry.getKey();
                CartItemDomain cartItem = entry.getValue();

                Item item = new Item()
                        .id(productId)
                        .quantity(cartItem.getQuantity())
                        .unitPrice(cartItem.getUnitPrice());

                items.add(item);
            }
        }
        return items;
    }

}
