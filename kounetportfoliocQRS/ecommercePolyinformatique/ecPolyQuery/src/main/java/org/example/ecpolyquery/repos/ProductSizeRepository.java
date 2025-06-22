package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ProductSizeRepository extends JpaRepository<ProductSize, String> {

  List<ProductSize> findBySubcategoryIdAndSocialGroupId(String subcategoryId, String socialGroupId);
  // News, pour les 30 derniers jours
  @Query("SELECT p FROM Stock s join ProductSize p where s.createdDate <=30 +:fromDate")
  List<ProductSize> findAllNewsProducts(@Param("fromDate") Date fromDate);

  // Sale
  @Query("SELECT p FROM ProductSize p WHERE p.promoPrice IS NOT NULL AND p.promoPrice < p.price")
  List<ProductSize> findAllSaleProducts();

}
