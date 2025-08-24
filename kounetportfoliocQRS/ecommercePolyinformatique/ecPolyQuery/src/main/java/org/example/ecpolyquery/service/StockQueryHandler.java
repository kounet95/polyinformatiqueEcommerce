package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Stock;
import org.example.ecpolyquery.query.*;
import org.example.ecpolyquery.repos.StockRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StockQueryHandler {

  private final StockRepository stockRepository;

  @QueryHandler
  public Page<StockDTO> on(GetAllStocksQuery query) {
    Pageable pageable = PageRequest.of(query.getPage(), query.getSize());
    Page<Stock> stocks = stockRepository.findAll(pageable);

    return stocks.map(this::toDto); // map chaque entité en DTO
  }

  private StockDTO toDto(Stock entity) {
    if (entity == null) return null;
    StockDTO dto = new StockDTO();
    dto.setId(entity.getId());
    dto.setQuantity(entity.getQuantity());
    dto.setProductSizeId(entity.getProductSize() != null ? entity.getProductSize().getId() : null);
    dto.setSupplierId(entity.getSupplier() != null ? entity.getSupplier().getId() : null);
    dto.setPromoPrice(entity.getPromoPrice());
    dto.setSupplyId(entity.getSupply() != null ? entity.getSupply().getId() : null);
    return dto;
  }


  @QueryHandler
  public Stock on(GetStockByIdQuery query) {
    log.debug("Handling GetStockByIdQuery: {}", query.getId());
    Optional<Stock> optionalStock = stockRepository.findById(query.getId());
    return optionalStock
      .orElseThrow(() -> new RuntimeException("Stock not found with id: " + query.getId()));
  }

  @QueryHandler
  public List<Stock> on(GetNewArrivalsStockQuery query) {
    log.debug("Handling GetNewArrivalsStockQuery since {}", query.getSince());
    return stockRepository.findByCreatedDateAfter(query.getSince());
  }

  @QueryHandler
  public List<Stock> on(GetOnSaleStockQuery query) {
    log.debug("Handling GetOnSaleStockQuery");
    return stockRepository.findOnSale();
  }
  @QueryHandler
  public Stock handle(GetStockByProductSizeIdQuery query) {
    return stockRepository.findByProductSize_Id(query.getId())
      .orElseThrow(() -> new RuntimeException("Stock not found for productSizeId " + query.getId()));
  }

}
