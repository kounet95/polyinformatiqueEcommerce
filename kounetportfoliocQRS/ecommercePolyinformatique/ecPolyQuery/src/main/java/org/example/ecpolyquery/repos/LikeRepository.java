package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Customer;

import org.example.ecpolyquery.entity.LikeProduct;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeProduct,String> {

  boolean existsByCustomerAndProduct(Customer customer, ProductSize product);
  long countByProduct(ProductSize product);
  void deleteByCustomerAndProduct(Customer customer, ProductSize product);
}
