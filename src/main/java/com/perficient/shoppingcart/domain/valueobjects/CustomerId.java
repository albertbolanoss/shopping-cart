package com.perficient.shoppingcart.domain.valueobjects;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerId {
    private final @NotNull String id;
}
