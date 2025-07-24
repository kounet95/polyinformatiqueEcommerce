package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, String> {


    List<Stock> findByCreatedDateAfter(LocalDateTime date);

  // On récupère les stocks dont le prix promo est inférieur au prix normal (donc en promo)
  @Query("SELECT s FROM Stock s WHERE s.promoPrice > 0 AND s.promoPrice < s.purchasePrice")
  List<Stock> findOnSale();
}
