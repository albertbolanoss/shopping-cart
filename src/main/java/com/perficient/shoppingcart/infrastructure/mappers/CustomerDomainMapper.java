package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.application.api.model.AddCustomerReq;
import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;
import com.perficient.shoppingcart.domain.valueobjects.CustomerIdDomain;
import com.perficient.shoppingcart.infrastructure.entities.Customer;

import java.util.Optional;

public class CustomerDomainMapper {
    public static CustomerDomain convertFromARequest(AddCustomerReq addUserReq) {
        return Optional.ofNullable(addUserReq)
            .map(addCustomerReq ->
                    new CustomerDomain(new CustomerIdDomain(null),
                            addCustomerReq.getFirstName(),
                            addCustomerReq.getLastName(),
                            addCustomerReq.getEmail(),
                            addCustomerReq.getPassword(),
                            addCustomerReq.getPhone(),
                            Boolean.TRUE))
                .orElse(null);
    }

    public static CustomerDomain convertFromEntity(Customer customer) {
        return Optional.ofNullable(customer)
                .map(entity ->
                        new CustomerDomain(new CustomerIdDomain(entity.getId()),
                                entity.getFirstName(),
                                entity.getLastName(),
                                entity.getEmail(),
                                entity.getPassword(),
                                entity.getPhone(),
                                entity.isActive()))
                .orElse(null);
    }
}
