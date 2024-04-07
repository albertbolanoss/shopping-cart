package com.ecommerce.customer.infrastructure.mappers;

import com.ecommerce.customer.domain.valueobjects.CustomerDomain;
import com.ecommerce.customer.domain.valueobjects.CustomerIdDomain;
import com.ecommerce.customer.infrastructure.entities.Customer;
import com.perficient.shoppingcart.application.api.model.AddUserReq;

import java.util.Optional;

public class CustomerDomainMapper {
    public static CustomerDomain convertFromARequest(AddUserReq addUserReq) {
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
