package com.ecommerce.shoppingcart.infrastructure.mappers;

import com.ecommerce.shoppingcart.domain.valueobjects.CustomerDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerIdDomain;
import com.ecommerce.shoppingcart.infrastructure.entities.Customer;

import java.util.Optional;

public class CustomerEntityMapper {
    public static Customer convertFromDomain(CustomerDomain customerDomain) {
        var customerOptional = Optional.ofNullable(customerDomain);

        var customerId = customerOptional
                .map(CustomerDomain::getCustomerId)
                .orElse(null);

        var id = Optional.ofNullable(customerId)
                .map(CustomerIdDomain::getId)
                .orElse(null);

        var active = customerOptional
                .map(CustomerDomain::getActive)
                .orElse(false);

        return customerOptional
                .map(domain -> new Customer(
                        id,
                        domain.getFirstName(),
                        domain.getLastName(),
                        domain.getEmail(),
                        domain.getPassword(),
                        domain.getPhone(),
                        active))
                .orElse(null);
    }
}
