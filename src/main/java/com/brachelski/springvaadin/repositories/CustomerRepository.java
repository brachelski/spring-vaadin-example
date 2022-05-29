package com.brachelski.springvaadin.repositories;

import com.brachelski.springvaadin.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);

    List<Customer> findByFirstNameStartsWithIgnoreCase(String firstName);
}
