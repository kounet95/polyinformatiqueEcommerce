package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, String> {
}