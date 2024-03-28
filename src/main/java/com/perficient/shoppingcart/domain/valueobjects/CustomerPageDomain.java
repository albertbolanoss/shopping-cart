package com.perficient.shoppingcart.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CustomerPageDomain {
    private PageResponseDomain pageResponseDomain;
    private List<CustomerDomain> customerDomains;
}
