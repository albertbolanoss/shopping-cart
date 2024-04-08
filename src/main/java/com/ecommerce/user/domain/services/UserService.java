package com.ecommerce.user.domain.services;

import com.ecommerce.user.domain.valueobjects.UserDomain;
import com.ecommerce.user.domain.valueobjects.UserPageDomain;
import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import com.ecommerce.shared.domain.exceptions.AlreadyExistException;
import com.ecommerce.user.domain.repositories.UserDomainRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

/**
 * Customer domain services
 */
@Service
@Validated
public class UserService {
    /**
     * The Customer domain repository
     */
    private  final UserDomainRepository userDomainRepository;

    /**
     * Constructor
     * @param userDomainRepository customer domain repository
     */
    @Autowired
    public UserService(UserDomainRepository userDomainRepository) {
        this.userDomainRepository = userDomainRepository;
    }

    /**
     * Register a new customer to the business
     * @param userDomain the customer to add
     */
    public void register(@NotNull @Valid UserDomain userDomain) {
        if (userDomain.getFirstName() == null
                || userDomain.getLastName() == null
                || userDomain.getEmail() == null)
            throw new IllegalArgumentException("Missing required customer information (first name, last name, email)");


        Optional.ofNullable(userDomainRepository.findByEmail(userDomain.getEmail()))
            .ifPresent(value -> {
                var message = String.format("The customer email (%s) is already registered", value.getEmail());
                throw new AlreadyExistException(message);
            });

        userDomainRepository.save(userDomain);
    }

    /**
     * Get the customer filter by first name, last name, email and page parameters
     * @param userReqFilterDomain the customer request filter domain
     * @return a customer page domain
     */
    public UserPageDomain findByFilters(UserReqFilterDomain userReqFilterDomain) {
        return userDomainRepository.findByFilters(userReqFilterDomain);
    }
}
