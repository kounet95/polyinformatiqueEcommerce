package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.AddStockCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/stock/command")

public class Stock {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public Stock(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<String> addStock(@Valid @RequestBody StockDTO stock) {
        String stockId = UUID.randomUUID().toString();
        StockDTO stockDTO = new StockDTO(
                stockId,
                stock.getDesignation(),
                stock.getProductSizeId(),
                stock.getSupplierId(),
                stock.getPurchasePrice(),
                stock.getPromoPrice(),
                stock.getQuantity(),
                stock.getAddressId()
        );
        AddStockCommand command = new AddStockCommand(stockId, stockDTO);
        return commandGateway.send(command);
    }

    @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + exception.getMessage());
    }
}
