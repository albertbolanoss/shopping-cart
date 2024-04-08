package com.ecommerce.user.application;

import com.ecommerce.shared.domain.exceptions.AlreadyExistException;
import com.ecommerce.user.domain.services.UserService;
import com.ecommerce.user.domain.valueobjects.UserDomain;
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
public class RegisterUserApp {
    /**
     * The customer service domain
     */
    private final UserService userService;

    /**
     *
     * @param userService customer service domain
     */
    @Autowired
    public RegisterUserApp(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a customer domain
     * @param userDomain the customer to register
     */
    public void register(@NotNull @Valid UserDomain userDomain) {
        try {
            userService.register(userDomain);
        } catch (AlreadyExistException ex) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, ex.getMessage());
        } catch (ConstraintViolationException | IllegalArgumentException ex) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
