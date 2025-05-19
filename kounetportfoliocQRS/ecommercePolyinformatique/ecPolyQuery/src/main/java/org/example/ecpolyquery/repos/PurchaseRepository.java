package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {
}