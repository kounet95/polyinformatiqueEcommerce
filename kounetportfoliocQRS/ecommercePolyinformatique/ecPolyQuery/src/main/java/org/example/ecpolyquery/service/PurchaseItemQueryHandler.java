package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.PurchaseItem;
import org.example.ecpolyquery.query.GetAllPurchaseItemsQuery;
import org.example.ecpolyquery.query.GetPurchaseItemByIdQuery;
import org.example.ecpolyquery.repos.PurchaseItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class PurchaseItemQueryHandler {

    private final PurchaseItemRepository purchaseItemRepository;

    @QueryHandler
    public List<PurchaseItem> on(GetAllPurchaseItemsQuery query) {
        log.debug("Handling GetAllPurchaseItemsQuery");
        return purchaseItemRepository.findAll();
    }

    @QueryHandler
    public PurchaseItem on(GetPurchaseItemByIdQuery query) {
        log.debug("Handling GetPurchaseItemByIdQuery: {}", query.getId());
        Optional<PurchaseItem> optionalPurchaseItem = purchaseItemRepository.findById(query.getId());
        return optionalPurchaseItem
                .orElseThrow(() -> new RuntimeException("PurchaseItem not found with id: " + query.getId()));
    }
}