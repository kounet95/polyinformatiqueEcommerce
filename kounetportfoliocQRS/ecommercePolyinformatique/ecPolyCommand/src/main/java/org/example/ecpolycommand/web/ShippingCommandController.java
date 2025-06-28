package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateShippingCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteShippingCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ShippingDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/shipping/command")
public class ShippingCommandController {

  private final CommandGateway commandGateway;
  private final EventStore eventStore;

  public ShippingCommandController(CommandGateway commandGateway, EventStore eventStore) {
    this.commandGateway = commandGateway;
    this.eventStore = eventStore;
  }

  @PostMapping("/create")
  public CompletableFuture<String> createShipping(@Valid @RequestBody ShippingDTO shipping) {
    String shippingId = UUID.randomUUID().toString();
    ShippingDTO shippingDTO = new ShippingDTO(
      shippingId,
      shipping.getOrderId(),
      shipping.getEstimatedDeliveryDate(),
      shipping.getShippingDate(),
      shipping.getCreatedAt(),
      shipping.getOrderStatus()
    );
    CreateShippingCommand command = new CreateShippingCommand(shippingId, shippingDTO);
    return commandGateway.send(command);
  }

  @DeleteMapping("/{shippingId}")
  public CompletableFuture<String> deleteShipping(@PathVariable String shippingId) {
    DeleteShippingCommand command = new DeleteShippingCommand(shippingId);
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
