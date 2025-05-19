package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.ReceivePurchaseCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.PurchaseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/purchase/command")
@CrossOrigin
public class Purchase {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public Purchase(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/receive")
    public CompletableFuture<String> receivePurchase(@Valid @RequestBody PurchaseDTO purchase) {
        String purchaseId = UUID.randomUUID().toString();
        PurchaseDTO purchaseDTO = new PurchaseDTO(
                purchaseId,
                purchase.getSupplierId(),
                purchase.getCreatedAt(),
                purchase.getStatus(),
                purchase.getTotal()
        );
        ReceivePurchaseCommand command = new ReceivePurchaseCommand(purchaseId, purchaseDTO);
        return commandGateway.send(command);
    }

    @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(" Error: " + exception.getMessage());
    }
}