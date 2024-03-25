package com.perficient.shoppingcart.domain.repositories;

import com.perficient.shoppingcart.infrastructure.entities.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepositoryExt {
    @Query("select o from Customer o")
    List<Customer> getAllCustomers();
}
