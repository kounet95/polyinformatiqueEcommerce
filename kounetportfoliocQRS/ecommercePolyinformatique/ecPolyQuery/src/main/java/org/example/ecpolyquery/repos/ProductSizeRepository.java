package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSize, String> {
}