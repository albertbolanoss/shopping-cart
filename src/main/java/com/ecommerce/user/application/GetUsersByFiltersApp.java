package com.ecommerce.user.application;

import com.ecommerce.user.domain.repositories.UserDomainRepository;
import com.ecommerce.user.domain.valueobjects.UserPageDomain;
import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUsersByFiltersApp {
    /**
     * The user domain repository
     */
    private final UserDomainRepository userDomainRepository;

    /**
     *
     * @param userDomainRepository user domain repository
     */
    @Autowired
    public GetUsersByFiltersApp(UserDomainRepository userDomainRepository) {
        this.userDomainRepository = userDomainRepository;
    }

    public UserPageDomain findByFilter(UserReqFilterDomain userReqFilterDomain) {
        return userDomainRepository.findByFilters(userReqFilterDomain);
    }
}
