package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.*;
import org.example.ecpolyquery.repos.*;
import org.example.ecpolyquery.query.GetAllStocksQuery;
import org.example.ecpolyquery.query.GetStockByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.StockIncreasedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.StockDecreasedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StockService {

  private final StockRepository stockRepository;
  private final SupplierRepository supplierRepository;
  private final ProductSizeRepository productSizeRepository;
  private final AddressRepository addressRepository;
  private final SupplyRepository supplyRepository;
  @EventHandler
  public void on(StockIncreasedEvent event) {
    log.debug("Handling StockIncreasedEvent: {}", event.getId());
    StockDTO dto = event.getStockDTO();

    try {
      // Get or create placeholder Supply
      Supply supply = supplyRepository.findById(dto.getSupplyId())
        .orElseGet(() -> {
          log.warn("Supply not found yet: {}, creating placeholder", dto.getSupplyId());
          Supply placeholderSupply = Supply.builder()
            .id(dto.getSupplyId())
            .name("Pending")
            .build();
          return supplyRepository.save(placeholderSupply);
        });

      // Get ProductSize safely
      ProductSize productSize = productSizeRepository.findById(dto.getProductSizeId())
        .orElseThrow(() -> new IllegalStateException("ProductSize not found, will retry later"));

      // Get Supplier safely
      Supplier supplier = supplierRepository.findById(dto.getSupplierId())
        .orElseThrow(() -> new IllegalStateException("Supplier not found, will retry later"));

      // All required entities exist, proceed with stock creation
      Stock stock = Stock.builder()
        .id(event.getId())
        .purchasePrice(dto.getPurchasePrice())
        .supply(supply) // <- always not null now
        .promoPrice(dto.getPromoPrice())
        .quantity(dto.getQuantity())
        .createdDate(LocalDateTime.now())
        .productSize(productSize)
        .supplier(supplier)
        .build();

      stockRepository.save(stock);
      log.info(" Stock increased and saved with ID: {}", event.getId());

    } catch (Exception e) {
      log.error("Error processing StockIncreasedEvent for stock ID {}: {}", event.getId(), e.getMessage(), e);
      // Let the event be retried by not acknowledging it
      throw e;
    }
  }


  @EventHandler
  public void on(StockDecreasedEvent event) {
    log.debug("Handling StockDecreasedEvent: {}", event.getId());
    StockDTO dto = event.getStockDTO();

    try {
      Optional<Stock> stockOpt = stockRepository.findById(event.getId());
      if (!stockOpt.isPresent()) {
        log.warn("Stock with ID {} not found for quantity update! Will retry later.", event.getId());
        return; // Exit and let the event be retried later
      }

      Stock stock = stockOpt.get();
      stock.setQuantity(stock.getQuantity() - dto.getQuantity()); // Subtract the quantity instead of setting it directly

      // Ensure quantity doesn't go below zero
      if (stock.getQuantity() < 0) {
        log.warn("Stock quantity would go below zero for ID {}. Setting to 0.", event.getId());
        stock.setQuantity(0);
      }

      stockRepository.save(stock);
      log.info("Stock decreased and updated for ID: {}", event.getId());
    } catch (Exception e) {
      log.error("Error processing StockDecreasedEvent for stock ID {}: {}", event.getId(), e.getMessage(), e);
      // Let the event be retried by not acknowledging it
      throw e;
    }
  }

  @QueryHandler
  public List<Stock> handle(GetAllStocksQuery query) {
    return stockRepository.findAll();
  }

  @QueryHandler
  public Stock handle(GetStockByIdQuery query) {
    return stockRepository.findById(query.getId()).orElse(null);
  }
}
