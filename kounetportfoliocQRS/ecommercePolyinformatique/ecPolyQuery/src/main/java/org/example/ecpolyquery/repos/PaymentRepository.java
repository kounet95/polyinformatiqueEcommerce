package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,String> {

}
