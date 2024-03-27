package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.domain.valueobjects.CustomerId;
import com.perficient.shoppingcart.infrastructure.entities.Customer;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Function;

public class CustomerEntityMapper {
    public static Customer convertFromDomain(com.perficient.shoppingcart.domain.valueobjects.Customer customer) {
        return Optional.of(customer)
                .map(domain -> new com.perficient.shoppingcart.infrastructure.entities.Customer(
                        Optional.ofNullable(customer.getCustomerId())
                                .map(CustomerId::getId)
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
