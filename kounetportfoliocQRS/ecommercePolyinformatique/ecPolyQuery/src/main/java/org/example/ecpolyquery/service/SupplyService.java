package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.entity.Stock;
import org.example.ecpolyquery.entity.Supply;
import org.example.ecpolyquery.repos.StockRepository;
import org.example.ecpolyquery.repos.SupplyRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplyDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.CreatedSupplyEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.StockDecreasedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SupplyService {

  private StockRepository stockRepository;
  private SupplyRepository supplyRepository;

  @EventHandler
  public void on(CreatedSupplyEvent event){
    log.debug("Handling SupplierCreatedEvent: {}", event.getId());
    SupplyDTO dto = event.getSupplyDTO();

    try {
      // Check if supply already exists to avoid duplicates
      if (supplyRepository.existsById(event.getId())) {
        log.info("Supply with ID {} already exists, skipping creation", event.getId());
        return;
      }

      List<Stock> stocks = null;
      if (dto.getStocksIds() != null && !dto.getStocksIds().isEmpty()) {
        stocks = stockRepository.findAllById(dto.getStocksIds());
        // Log if not all stocks were found
        if (stocks.size() < dto.getStocksIds().size()) {
          log.warn("Not all stocks were found for supply {}. Found {}/{} stocks.",
                  event.getId(), stocks.size(), dto.getStocksIds().size());
          // Continue anyway, as stocks might be created later
        }
      }

      LocalDateTime createdAt = dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now();
      Supply supply = Supply.builder()
        .id(event.getId())
        .name(dto.getName())
        .created_at(createdAt)
        .stockList(stocks)
        .build();

      supplyRepository.save(supply);
      log.info("Supply created with ID: {}", supply.getId());
    } catch (Exception e) {
      log.error("Error processing CreatedSupplyEvent for supply ID {}: {}", event.getId(), e.getMessage(), e);
      // Let the event be retried by not acknowledging it
      throw e;
    }
  }
}
