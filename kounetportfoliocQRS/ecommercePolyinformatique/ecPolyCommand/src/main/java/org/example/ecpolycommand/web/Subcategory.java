package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateSubcategoryCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SubcategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/subcategory/command")

public class Subcategory {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public Subcategory(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<String> createSubcategory(@Valid @RequestBody SubcategoryDTO subcategory) {
        String subcategoryId = UUID.randomUUID().toString();
        SubcategoryDTO subcategoryDTO = new SubcategoryDTO(
                subcategoryId,
                subcategory.getName(),
                subcategory.getCategoryId()
        );
        CreateSubcategoryCommand command = new CreateSubcategoryCommand(subcategoryId, subcategoryDTO);
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
