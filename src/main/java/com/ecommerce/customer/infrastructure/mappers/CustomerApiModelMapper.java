package com.ecommerce.customer.infrastructure.mappers;

import com.perficient.shoppingcart.application.api.model.CustomerReq;
import com.ecommerce.customer.domain.valueobjects.CustomerDomain;
import com.ecommerce.customer.domain.valueobjects.CustomerIdDomain;

import java.util.Optional;

/**
 * Convert to customer api model
 */
public class CustomerApiModelMapper {
    public static CustomerReq convertFromDomain(CustomerDomain customerDomain) {
        return Optional.ofNullable(customerDomain)
                .map(domain -> new CustomerReq()
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