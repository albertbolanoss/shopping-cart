package com.perficient.shoppingcart.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Product {
    private UUID id;
    private String name;
    private String description;
    private Boolean IsStockAvailability;
}
