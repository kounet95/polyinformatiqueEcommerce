package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Shipping;
import org.example.ecpolyquery.query.GetAllShippingsQuery;
import org.example.ecpolyquery.query.GetShippingByIdQuery;
import org.example.ecpolyquery.repos.ShippingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ShippingQueryHandler {

    private final ShippingRepository shippingRepository;

    @QueryHandler
    public List<Shipping> on(GetAllShippingsQuery query) {
        log.debug("Handling GetAllShippingsQuery");
        return shippingRepository.findAll();
    }

    @QueryHandler
    public Shipping on(GetShippingByIdQuery query) {
        log.debug("Handling GetShippingByIdQuery: {}", query.getId());
        Optional<Shipping> optionalShipping = shippingRepository.findById(query.getId());
        return optionalShipping
                .orElseThrow(() -> new RuntimeException("Shipping not found with id: " + query.getId()));
    }
}