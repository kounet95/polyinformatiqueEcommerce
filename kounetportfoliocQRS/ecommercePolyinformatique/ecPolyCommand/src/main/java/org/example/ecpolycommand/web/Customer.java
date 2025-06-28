package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCustomerCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.CustomerUpdatedCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCustomerCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/customer/command")

public class Customer {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public Customer(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<String> createCustomer(@Valid @RequestBody CustomerEcommerceDTO author) {

        CustomerEcommerceDTO authorDTO = new CustomerEcommerceDTO(
                author.getId(),
                author.getFirstname(),
                author.getEmail(),
                author.getLastname(),
                author.getPhone(),
                author.getAddressId(),
                author.getCreatedAt()
        );
        CreateCustomerCommand command = new CreateCustomerCommand(authorDTO);
        return commandGateway.send(command);
    }

    @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @DeleteMapping("/delete/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<String> deleteCustomer(@PathVariable String customerId) {
        return commandGateway.send(new DeleteCustomerCommand(customerId));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(" Error: " + exception.getMessage());
    }

  @PutMapping("/update/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> updateCustomer(
    @Valid @RequestBody CustomerEcommerceDTO author,
    @PathVariable String id) {
      CustomerEcommerceDTO customerDTO = new CustomerEcommerceDTO(
              id,
              author.getFirstname(),
              author.getEmail(),
              author.getLastname(),
              author.getPhone(),
              author.getAddressId(),
              author.getCreatedAt()
      );
      CustomerUpdatedCommand command = new CustomerUpdatedCommand(id, customerDTO);
      return commandGateway.send(command);
  }

}
