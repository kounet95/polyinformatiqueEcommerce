package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}