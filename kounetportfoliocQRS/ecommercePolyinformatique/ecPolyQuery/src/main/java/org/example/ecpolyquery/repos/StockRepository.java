package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, String> {


}
