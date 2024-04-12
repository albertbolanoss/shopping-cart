package com.ecommerce.user.infrastructure.repository;

import com.ecommerce.shared.api.pageable.PageRequestCreator;
import com.ecommerce.shared.domain.valueobjects.PageResponseDomain;
import com.ecommerce.user.domain.repositories.UserDomainRepository;
import com.ecommerce.user.domain.valueobjects.NewUserDomain;
import com.ecommerce.user.domain.valueobjects.UserPageDomain;
import com.ecommerce.user.domain.valueobjects.UserReqFilterDomain;
import com.ecommerce.user.infrastructure.mappers.NewUserDomainMapper;
import com.ecommerce.user.infrastructure.mappers.UserEntityMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;


/**
 * Customer domain repository implementation
 */
@Service
@Validated
public class UserDomainRepositoryImpl implements UserDomainRepository {
    /**
     * Customer infrastructure repository
     */
    private final UserRepository userRepository;

    /**
     * Constructor
     * @param userRepository customer infrastructure repository
     */
    @Autowired
    public UserDomainRepositoryImpl(@NotNull @Valid UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save a customer domain in database
     * @param newUserDomain the customer domain
     */
    @Override
    public void save(NewUserDomain newUserDomain) {
        var customerEntity = UserEntityMapper.convertFromDomain(newUserDomain);
        userRepository.save(customerEntity);
    }

    /**
     * Find the first customer with the email
     * @param email the email to search
     * @return a customer domain
     */
    @Override
    public Optional<NewUserDomain> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(NewUserDomainMapper::convertFromEntity);
    }

    @Override
    public UserPageDomain findByFilters(UserReqFilterDomain userReqFilterDomain) {
        var pageRequest = PageRequestCreator.create(
                userReqFilterDomain.getPageNumber(),
                userReqFilterDomain.getPageSize(),
                userReqFilterDomain.getSort()
        );
        var customerPageable = userRepository.findByFirstNameLastNameEmail(
                userReqFilterDomain.getFirstName(),
                userReqFilterDomain.getLastName(),
                userReqFilterDomain.getEmail(),
                pageRequest
        );
        var customers = customerPageable.getContent()
                .stream().map(NewUserDomainMapper::convertFromEntity)
                .toList();

        var pageResponseDomain = new PageResponseDomain(customerPageable.getTotalElements(),
                customerPageable.getTotalPages());

        return new UserPageDomain(customers, pageResponseDomain, userReqFilterDomain);
    }

}
