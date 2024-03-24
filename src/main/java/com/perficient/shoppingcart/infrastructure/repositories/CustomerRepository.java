package com.perficient.shoppingcart.infrastructure.repositories;

import com.perficient.shoppingcart.infrastructure.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID>, CustomerRepositoryExt {

}
