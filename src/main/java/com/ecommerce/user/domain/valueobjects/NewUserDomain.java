package com.ecommerce.user.domain.valueobjects;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represent a domain customer
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class NewUserDomain {
    /**
     * The user first name
     */
    private final @NotNull @Size(max = 125) String firstName;
    /**
     * The user last name
     */
    private final @NotNull @Size(max = 125) String lastName;
    /**
     * The user email
     */
    private final @NotNull @Size(max = 255) String email;
    /**
     * The user password
     */
    private final @NotNull @Size(max = 255) String password;
    /**
     * The user phone
     */
    private final @Size(max = 40) String phone;

    /**
     * indicate if the user is active or inactive
     */
    private boolean active;

}
