package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.entity.Like;
import org.example.ecpolyquery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

  boolean existsByCustomerAndProduct(Customer customer, Product product);
  long countByProduct(Product product);
  void deleteByCustomerAndProduct(Customer customer, Product product);
}
