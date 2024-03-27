package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.application.api.model.AddCustomerReq;
import com.perficient.shoppingcart.domain.valueobjects.Customer;

import java.util.Optional;
import java.util.function.Function;

public class CustomerDomainMapper {
    public static Customer convertFromARequest(AddCustomerReq addUserReq) {
        return Optional.of(addUserReq)
            .map(addCustomerReq ->
                    new Customer(null,
                            addCustomerReq.getFirstName(),
                            addCustomerReq.getLastName(),
                            addCustomerReq.getEmail(),
                            addCustomerReq.getPassword(),
                            addCustomerReq.getPhone(),
                            Boolean.TRUE))
                .orElse(null);
    }
}
