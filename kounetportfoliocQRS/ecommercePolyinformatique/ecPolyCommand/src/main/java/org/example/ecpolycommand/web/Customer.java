package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCustomerCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCustomerCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/customer/command")
@CrossOrigin
public class Customer {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public Customer(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createCustomer(@Valid @RequestBody CustomerEcommerceDTO author) {
        String authorId = UUID.randomUUID().toString();
        CustomerEcommerceDTO authorDTO = new CustomerEcommerceDTO(
                authorId,
                author.getFirstname(),
                author.getEmail(),
                author.getLastname(),
                author.getPhone(),
                author.getShippingAddress(),
                author.getBillingAddress(),
                author.getCreatedAt()
        );
        CreateCustomerCommand command = new CreateCustomerCommand(authorId, authorDTO);
        return commandGateway.send(command);
    }

    @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @DeleteMapping("/delete/{customerId}")
    public CompletableFuture<String> deleteCustomer(@PathVariable String customerId) {
        return commandGateway.send(new DeleteCustomerCommand(customerId));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(" Error: " + exception.getMessage());
    }
}