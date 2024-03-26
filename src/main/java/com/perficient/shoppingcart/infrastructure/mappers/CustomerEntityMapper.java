package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.domain.valueobjects.Customer;
import com.perficient.shoppingcart.infrastructure.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerEntityMapper {
    @Mapping(target = "id", source = "customerId.id")
    User convertFromDomain(Customer customer);
}
