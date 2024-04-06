package com.ecommerce.shoppingcart.infrastructure.repository;

import com.ecommerce.shoppingcart.infrastructure.api.pageable.PageRequestCreator;
import com.ecommerce.shoppingcart.infrastructure.mappers.CustomerEntityMapper;
import com.ecommerce.shoppingcart.domain.repositories.CustomerDomainRepository;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerPageDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.CustomerReqFilterDomain;
import com.ecommerce.shoppingcart.domain.valueobjects.PageResponseDomain;
import com.ecommerce.shoppingcart.infrastructure.mappers.CustomerDomainMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.stream.Collectors;


/**
 * Customer domain repository implementation
 */
@Service
@Validated
public class CustomerDomainRepositoryImpl implements CustomerDomainRepository {
    /**
     * Customer infrastructure repository
     */
    private final CustomerRepository customerRepository;

    /**
     * Constructor
     * @param customerRepository customer infrastructure repository
     */
    @Autowired
    public CustomerDomainRepositoryImpl(@NotNull @Valid CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Save a customer domain in database
     * @param customerDomain the customer domain
     */
    @Override
    public void save(CustomerDomain customerDomain) {
        var customerEntity = CustomerEntityMapper.convertFromDomain(customerDomain);
        customerRepository.save(customerEntity);
    }

    /**
     * Find the first customer with the email
     * @param email the email to search
     * @return a customer domain
     */
    @Override
    public CustomerDomain findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(CustomerDomainMapper::convertFromEntity)
                .orElse(null);
    }

    @Override
    public CustomerPageDomain findByFilters(CustomerReqFilterDomain customerReqFilterDomain) {
        var pageRequest = PageRequestCreator.create(
                customerReqFilterDomain.getPageNumber(),
                customerReqFilterDomain.getPageSize(),
                customerReqFilterDomain.getSort()
        );
        var customerPageable = customerRepository.findByCustomersByFirstNameLastNameEmail(
                customerReqFilterDomain.getFirstName(),
                customerReqFilterDomain.getLastName(),
                customerReqFilterDomain.getEmail(),
                pageRequest
        );
        var customers = customerPageable.getContent()
                .stream().map(CustomerDomainMapper::convertFromEntity)
                .collect(Collectors.toList());

        var pageResponseDomain = new PageResponseDomain(customerPageable.getTotalElements(),
                customerPageable.getTotalPages());

        return new CustomerPageDomain(customers, pageResponseDomain, customerReqFilterDomain);
    }

}
