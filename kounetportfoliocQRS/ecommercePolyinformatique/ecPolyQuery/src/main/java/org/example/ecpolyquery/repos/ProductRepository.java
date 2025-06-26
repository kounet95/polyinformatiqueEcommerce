package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SizeProd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product>  {
//  @Query("SELECT DISTINCT s.product FROM Stock s WHERE s.createdDate >= :fromProduct")
// Page<Product> finAllByKeyword(String keyword, Pageable pageable);
}
