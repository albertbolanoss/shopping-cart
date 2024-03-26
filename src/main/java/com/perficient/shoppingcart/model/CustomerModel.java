package com.perficient.shoppingcart.model;

import com.perficient.shoppingcart.valueobjects.CustomerId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class CustomerModel {
    private CustomerId customerId;
    private final @NotNull @Size(max = 100) String firstName;
    private final @NotNull @Size(max = 100) String lastName;
    private final @NotNull @Size(max = 255) String email;
    private final @NotNull @Size(max = 255) String password;
    private @Size(max = 20) String phone;
    private boolean active;
}
