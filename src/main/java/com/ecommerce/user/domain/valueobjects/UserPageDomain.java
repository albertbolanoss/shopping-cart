package com.ecommerce.user.domain.valueobjects;

import com.ecommerce.shared.domain.valueobjects.PageResponseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserPageDomain {
    private List<UserDomain> userDomains;
    private PageResponseDomain pageResponseDomain;
    private UserReqFilterDomain userReqFilterDomain;
}
