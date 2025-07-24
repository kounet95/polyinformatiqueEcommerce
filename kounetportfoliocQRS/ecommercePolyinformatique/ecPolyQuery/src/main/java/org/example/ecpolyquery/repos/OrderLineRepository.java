package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, String> {
}