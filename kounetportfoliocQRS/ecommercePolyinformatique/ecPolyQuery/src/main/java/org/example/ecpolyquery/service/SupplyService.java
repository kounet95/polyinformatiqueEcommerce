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
      SupplyDTO  dto = event.getSupplyDTO();

  List<Stock> stocks = null;
  if (dto.getStocksIds() != null && !dto.getStocksIds() .isEmpty()) {
    stocks= stockRepository.findAllById(dto.getStocksIds());
  }
  LocalDateTime createdAt = dto.getCreatedAt() != null ? dto.getCreatedAt(): LocalDateTime.now();
   Supply supply = Supply.builder()
     .id(event.getId())
     .name(dto.getName())
     .created_at(createdAt)
     .stockList(stocks)

     .build();

   supplyRepository.save(supply);

  log.info("supply created with ID: {}", supply.getId());
      }
}
