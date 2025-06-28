package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.entity.Stock;
import org.example.ecpolyquery.entity.Supplier;
import org.example.ecpolyquery.repos.AddressRepository;
import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.example.ecpolyquery.repos.StockRepository;
import org.example.ecpolyquery.repos.SupplierRepository;
import org.example.ecpolyquery.query.GetAllStocksQuery;
import org.example.ecpolyquery.query.GetStockByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.StockIncreasedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.StockDecreasedEvent;
import org.springframework.stereotype.Service;

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
  @EventHandler
  public void on(StockIncreasedEvent event) {
    log.debug("Handling StockIncreasedEvent: {}", event.getId());
    StockDTO dto = event.getStockDTO();

    // Convertir le ProductSizeDTO du DTO en ProductSize de l'entité
    ProductSize productSize = productSizeRepository.findById(event.getStockDTO().getProductSizeId()).get();
    Optional<Supplier> supplierOpt = supplierRepository.findById(dto.getSupplierId());
    if (supplierOpt.isPresent()) {
      Stock stock = Stock.builder()
        .id(event.getId())
        .purchasePrice(dto.getPurchasePrice())
        .designation(dto.getDesignation())
        .promoPrice(dto.getPromoPrice())
        .productSize(productSize)
        .supplier(supplierOpt.get())
        .build();
      stockRepository.save(stock);
      log.info("Stock increased and saved with ID: {}", event.getId());
    } else {
      log.warn("Supplier with ID {} not found for Stock creation!", dto.getSupplierId());
    }
  }

  @EventHandler
  public void on(StockDecreasedEvent event) {
    log.debug("Handling StockDecreasedEvent: {}", event.getId());
    StockDTO dto = event.getStockDTO();
    stockRepository.findById(event.getId()).ifPresent(stock -> {
      stockRepository.save(stock);
      log.info("Stock decreased and updated for ID: {}", event.getId());
    });
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
