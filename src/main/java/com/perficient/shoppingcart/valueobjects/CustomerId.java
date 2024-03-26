package com.perficient.shoppingcart.valueobjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerId {
    private final @NotNull @Size(max = 36) String id;
}
