package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Stock;
import org.example.ecpolyquery.query.GetAllStocksQuery;
import org.example.ecpolyquery.query.GetStockByIdQuery;
import org.example.ecpolyquery.repos.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StockQueryHandler {

  private final StockRepository stockRepository;

  @QueryHandler
  public List<Stock> on(GetAllStocksQuery query) {
    log.debug("Handling GetAllStocksQuery");
    return stockRepository.findAll();
  }

  @QueryHandler
  public Stock on(GetStockByIdQuery query) {
    log.debug("Handling GetStockByIdQuery: {}", query.getId());
    Optional<Stock> optionalStock = stockRepository.findById(query.getId());
    return optionalStock
      .orElseThrow(() -> new RuntimeException("Stock not found with id: " + query.getId()));
  }
}
