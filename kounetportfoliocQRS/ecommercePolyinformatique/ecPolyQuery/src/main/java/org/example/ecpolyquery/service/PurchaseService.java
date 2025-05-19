package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Purchase;
import org.example.ecpolyquery.entity.Supplier;
import org.example.ecpolyquery.repos.PurchaseRepository;
import org.example.ecpolyquery.repos.SupplierRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.PurchaseDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.PurchaseReceivedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@Slf4j
@AllArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;

    @EventHandler
    public void on(PurchaseReceivedEvent event) {
        log.debug("Handling PurchaseReceivedEvent: {}", event.getId());
        PurchaseDTO purchaseDTO = event.getPurchaseDTO();

        Supplier supplier = supplierRepository.findById(purchaseDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + purchaseDTO.getSupplierId()));

        LocalDateTime createdAt = purchaseDTO.getCreatedAt() != null && !purchaseDTO.getCreatedAt().isEmpty() 
                ? LocalDateTime.parse(purchaseDTO.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME) 
                : LocalDateTime.now();

        Purchase purchase = Purchase.builder()
                .id(event.getId())
                .createdAt(createdAt)
                .status(purchaseDTO.getStatus())
                .total(purchaseDTO.getTotal())
                .supplier(supplier)
                .items(new ArrayList<>())
                .build();

        purchaseRepository.save(purchase);
        log.info("Purchase received with ID: {}", purchase.getId());
    }
}