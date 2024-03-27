package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;
import com.perficient.shoppingcart.domain.valueobjects.CustomerIdDomain;
import com.perficient.shoppingcart.infrastructure.entities.Customer;

import java.util.Optional;

public class CustomerEntityMapper {
    public static Customer convertFromDomain(CustomerDomain customerDomain) {
        return Optional.of(customerDomain)
                .map(domain -> new Customer(
                        Optional.ofNullable(customerDomain.getCustomerIdDomain())
                                .map(CustomerIdDomain::getId)
                                .orElse(null),
                        domain.getFirstName(),
                        domain.getLastName(),
                        domain.getEmail(),
                        domain.getPassword(),
                        domain.getPhone(),
                        domain.getActive()))
                .orElse(null);
    }
}
