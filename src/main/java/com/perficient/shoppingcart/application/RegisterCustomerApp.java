package com.perficient.shoppingcart.application;

import com.perficient.shoppingcart.domain.exceptions.AlreadyExistException;
import com.perficient.shoppingcart.domain.services.CustomerService;
import com.perficient.shoppingcart.domain.valueobjects.CustomerDomain;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Validated
public class RegisterCustomerApp {
    /**
     * The customer service domain
     */
    private final CustomerService customerService;

    /**
     *
     * @param customerService customer service domain
     */
    @Autowired
    public RegisterCustomerApp(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Register a customer domain
     * @param customerDomain the customer to register
     */
    public void register(@NotNull @Valid CustomerDomain customerDomain) {
        try {
            customerService.register(customerDomain);
        } catch (AlreadyExistException ex) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, ex.getMessage());
        } catch (ConstraintViolationException | IllegalArgumentException ex) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
