package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.AddProductToOrderCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteOrderLineCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/order-line/command")
public class OrderLine {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public OrderLine(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createOrderLine(@Valid @RequestBody OrderLineDTO orderLine) {
        String orderLineId = UUID.randomUUID().toString();
        OrderLineDTO orderLineDTO = new OrderLineDTO(
                orderLineId,
                orderLine.getOrderId(),
                orderLine.getProductId(),
                orderLine.getQty()
        );
        AddProductToOrderCommand command = new AddProductToOrderCommand(orderLineId, orderLineDTO);
        return commandGateway.send(command);
    }

    @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

  @DeleteMapping("/{orderLineId}")
  public CompletableFuture<String> deleteOrderLine(@PathVariable String orderLineId) {
    DeleteOrderLineCommand command = new DeleteOrderLineCommand(orderLineId);
    return commandGateway.send(command);
  }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(" Error: " + exception.getMessage());
    }
}
