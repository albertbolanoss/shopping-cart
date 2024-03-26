package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.infrastructure.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerEntityMapper {
    @Mapping(target = "id", source = "customerId.id")
    Customer convertFromDomain(com.perficient.shoppingcart.domain.valueobjects.Customer customer);
}
