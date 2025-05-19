package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}