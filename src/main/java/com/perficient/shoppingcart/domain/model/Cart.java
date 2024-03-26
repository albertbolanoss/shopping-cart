package com.perficient.shoppingcart.domain.model;

import com.perficient.shoppingcart.domain.valueobjects.CustomerId;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

@SessionScope
public class Cart {
    private CustomerId customerId;

    public record Item(int quantity, String product, BigDecimal unitPrice) {}

    private final ConcurrentLinkedDeque<Item> items = new ConcurrentLinkedDeque<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(int index) {
        if (index < 0 || index >= items.size()) {
            throw new IllegalArgumentException("Invalid index for item removal");
        }

        items.stream().skip(index).findFirst().ifPresent(items::remove);
    }

    public void removeProduct(Item item) {

    }

    public ConcurrentLinkedDeque<Item> getItemsInCart() {
        return items;
    }


    public List<Item> getProducts() {
        return Collections.emptyList();
    }

}
