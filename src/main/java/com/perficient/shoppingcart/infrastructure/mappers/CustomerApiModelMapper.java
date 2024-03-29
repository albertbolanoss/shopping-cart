package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.application.api.model.Customer;
import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;
import com.perficient.shoppingcart.domain.valueobjects.CustomerIdDomain;

import java.util.Optional;

/**
 * Convert to customer api model
 */
public class CustomerApiModelMapper {
    public static Customer convertFromDomain(CustomerDomain customerDomain) {
        return Optional.ofNullable(customerDomain)
                .map(domain -> new Customer()
                        .id(Optional.ofNullable(domain.getCustomerId())
                                .map(CustomerIdDomain::getId)
                                .orElse(null))
                        .email(domain.getEmail())
                        .firstName(domain.getFirstName())
                        .lastName(domain.getLastName())
                        .phone(domain.getPhone()))
                .orElse(null);
    }
}
