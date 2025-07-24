package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, String> {
}