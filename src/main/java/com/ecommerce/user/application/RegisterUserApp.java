package com.ecommerce.user.application;

import com.ecommerce.shared.domain.exceptions.AlreadyExistException;
import com.ecommerce.user.domain.model.UserModel;
import com.ecommerce.user.domain.repositories.UserDomainRepository;
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
     * The user domain repository
     */
    private  final UserDomainRepository userDomainRepository;

    /**
     *
     * @param userDomainRepository user domain repository
     */
    @Autowired
    public RegisterUserApp(UserDomainRepository userDomainRepository) {
        this.userDomainRepository = userDomainRepository;
    }

    /**
     * Register a customer domain
     * @param userDomain the customer to register
     */
    public void register(@NotNull @Valid UserDomain userDomain) {
        try {
            var userModel = new UserModel(userDomain, userDomainRepository);
            userModel.register();

        } catch (AlreadyExistException ex) {
            throw new HttpClientErrorException(HttpStatus.CONFLICT, ex.getMessage());
        } catch (ConstraintViolationException | IllegalArgumentException ex) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
