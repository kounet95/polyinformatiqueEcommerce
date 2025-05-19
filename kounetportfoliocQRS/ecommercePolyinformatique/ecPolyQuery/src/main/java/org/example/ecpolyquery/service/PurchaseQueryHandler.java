package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Purchase;
import org.example.ecpolyquery.query.GetAllPurchasesQuery;
import org.example.ecpolyquery.query.GetPurchaseByIdQuery;
import org.example.ecpolyquery.repos.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class PurchaseQueryHandler {

    private final PurchaseRepository purchaseRepository;

    @QueryHandler
    public List<Purchase> on(GetAllPurchasesQuery query) {
        log.debug("Handling GetAllPurchasesQuery");
        return purchaseRepository.findAll();
    }

    @QueryHandler
    public Purchase on(GetPurchaseByIdQuery query) {
        log.debug("Handling GetPurchaseByIdQuery: {}", query.getId());
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(query.getId());
        return optionalPurchase
                .orElseThrow(() -> new RuntimeException("Purchase not found with id: " + query.getId()));
    }
}