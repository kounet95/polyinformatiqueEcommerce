package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.OrderLine;
import org.example.ecpolyquery.query.GetAllOrderLinesQuery;
import org.example.ecpolyquery.query.GetOrderLineByIdQuery;
import org.example.ecpolyquery.repos.OrderLineRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderLineDeletedEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class OrderLineQueryHandler {

    private final OrderLineRepository orderLineRepository;

    @QueryHandler
    public List<OrderLine> on(GetAllOrderLinesQuery query) {
        log.debug("Handling GetAllOrderLinesQuery");
        return orderLineRepository.findAll();
    }

    @QueryHandler
    public OrderLine on(GetOrderLineByIdQuery query) {
        log.debug("Handling GetOrderLineByIdQuery: {}", query.getId());
        Optional<OrderLine> optionalOrderLine = orderLineRepository.findById(query.getId());
        return optionalOrderLine
                .orElseThrow(() -> new RuntimeException("OrderLine not found with id: " + query.getId()));
    }



}
