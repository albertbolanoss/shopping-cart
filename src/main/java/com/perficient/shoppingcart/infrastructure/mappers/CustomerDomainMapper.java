package com.perficient.shoppingcart.infrastructure.mappers;

import com.perficient.shoppingcart.application.api.model.AddCustomerReq;
import com.perficient.shoppingcart.domain.valueobjects.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerDomainMapper {
    @Mapping(target = "active", constant = "true")
    Customer convertFromARequest(AddCustomerReq addUserReq);
}
