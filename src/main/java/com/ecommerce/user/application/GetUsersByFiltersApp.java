package com.ecommerce.user.application;

import com.ecommerce.user.domain.services.UserService;
import com.ecommerce.user.domain.valueobjects.UserPageDomain;
import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUsersByFiltersApp {
    /**
     * The customer service domain
     */
    private final UserService userService;

    /**
     *
     * @param userService customer service domain
     */
    @Autowired
    public GetUsersByFiltersApp(UserService userService) {
        this.userService = userService;
    }

    public UserPageDomain findByFilter(UserReqFilterDomain userReqFilterDomain) {
        return userService.findByFilters(userReqFilterDomain);
    }
}
