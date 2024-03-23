package com.perficient.shoppingcart;

import com.perficient.shoppingcart.shared.ShoppingCart;

import java.math.BigDecimal;

public class ShoppingCartSimulation {

    public static void main(String[] args) {

        ShoppingCart shoppingCart1 = new ShoppingCart();

        shoppingCart1.addItem(2, "Banana", new BigDecimal("2000")); // 4.000
        shoppingCart1.addItem(3, "Orange", new BigDecimal("1000")); // 3.000
        shoppingCart1.addItem(3, "Strawberry", new BigDecimal("2000")); // 6.000

        // shoppingCart1.getItems().add(new ShoppingCart.Item(-2, "Pineapple", new BigDecimal("2000"))); // -4.000

        // shoppingCart1.getItems().add(null);

        shoppingCart1.getItems().forEach(item -> {
            System.out.println(item.product());
        });

        BigDecimal total = shoppingCart1.getTotal();

        System.out.println("Total: " + total); // 13.000

        BigDecimal totalPayment = shoppingCart1.getTotalWithFee("Cash", total);

        System.out.println("Total payment: " + totalPayment);

    }
}