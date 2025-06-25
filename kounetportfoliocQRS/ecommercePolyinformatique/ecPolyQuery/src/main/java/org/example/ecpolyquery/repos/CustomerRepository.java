package org.example.ecpolyquery.repos;

import jakarta.validation.constraints.Size;
import org.example.ecpolyquery.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findAllById(@Size(max = 255, message = "Customer field must be less than 255 characters") String customer);
}
