package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}