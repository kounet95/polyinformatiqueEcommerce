package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Subcategory;
import org.example.ecpolyquery.query.GetNewArrivalsStockQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, String> {
  List<Subcategory> findByCategory_Id(String categoryId);
}
