package com.ecommerce.customer.domain.valueobjects;

import com.ecommerce.shared.domain.valueobjects.PageResponseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CustomerPageDomain {
    private List<CustomerDomain> customerDomains;
    private PageResponseDomain pageResponseDomain;
    private CustomerReqFilterDomain customerReqFilterDomain;
}
