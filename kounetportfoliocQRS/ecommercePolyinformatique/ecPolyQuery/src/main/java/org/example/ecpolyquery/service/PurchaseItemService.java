package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Purchase;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.PurchaseItem;
import org.example.ecpolyquery.repos.PurchaseRepository;
import org.example.ecpolyquery.repos.ProductRepository;
import org.example.ecpolyquery.repos.PurchaseItemRepository;
import org.example.ecpolyquery.query.GetAllPurchaseItemsQuery;
import org.example.ecpolyquery.query.GetPurchaseItemByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.PurchaseItemDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.PurchaseItemCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.PurchaseItemDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.PurchaseItemUpdatedEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class PurchaseItemService {

  private final PurchaseItemRepository purchaseItemRepository;
  private final PurchaseRepository purchaseRepository;
  private final ProductRepository productRepository;

  @EventHandler
  public void on(PurchaseItemCreatedEvent event) {
    log.debug("Handling PurchaseItemCreatedEvent: {}", event.getId());
    PurchaseItemDTO dto = event.getPurchaseItemDTO();

    Optional<Purchase> purchaseOpt = purchaseRepository.findById(dto.getPurchaseId());
    Optional<Product> productOpt = productRepository.findById(dto.getProductId());

    if (purchaseOpt.isPresent() && productOpt.isPresent()) {
      PurchaseItem item = PurchaseItem.builder()
        .id(event.getId())
        .qty(dto.getQty())
        .unitPrice(dto.getUnitPrice())
        .purchase(purchaseOpt.get())
        .product(productOpt.get())
        .build();
      purchaseItemRepository.save(item);
      log.info("PurchaseItem created with ID: {}", event.getId());
    } else {
      if (!purchaseOpt.isPresent()) {
        log.warn("Purchase with ID {} not found!", dto.getPurchaseId());
      }
      if (!productOpt.isPresent()) {
        log.warn("Product with ID {} not found!", dto.getProductId());
      }
    }
  }

  @EventHandler
  public void on(PurchaseItemDeletedEvent event) {
    log.debug("Handling PurchaseItemDeletedEvent: {}", event.getId());
    purchaseItemRepository.findById(event.getId()).ifPresent(item -> {
      purchaseItemRepository.delete(item);
      log.info("PurchaseItem deleted with ID: {}", event.getId());
    });
  }

  @EventHandler
  public void on(PurchaseItemUpdatedEvent event) {
    log.debug("Handling PurchaseItemUpdatedEvent: {}", event.getId());
    PurchaseItemDTO dto = event.getDto();
    purchaseItemRepository.findById(event.getId()).ifPresent(item -> {
      item.setQty(dto.getQty());
      item.setUnitPrice(dto.getUnitPrice());

      // Si la relation Purchase ou Product a changé, on la met à jour
      if (!item.getPurchase().getId().equals(dto.getPurchaseId())) {
        purchaseRepository.findById(dto.getPurchaseId()).ifPresent(item::setPurchase);
      }
      if (!item.getProduct().getId().equals(dto.getProductId())) {
        productRepository.findById(dto.getProductId()).ifPresent(item::setProduct);
      }
      purchaseItemRepository.save(item);
      log.info("PurchaseItem updated with ID: {}", event.getId());
    });
  }

  @QueryHandler
  public List<PurchaseItem> handle(GetAllPurchaseItemsQuery query) {
    return purchaseItemRepository.findAll();
  }

  @QueryHandler
  public PurchaseItem handle(GetPurchaseItemByIdQuery query) {
    return purchaseItemRepository.findById(query.getId()).orElse(null);
  }
}
