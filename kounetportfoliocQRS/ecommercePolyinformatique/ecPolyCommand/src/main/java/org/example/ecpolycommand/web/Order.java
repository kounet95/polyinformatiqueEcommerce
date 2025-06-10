package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/order/command")

public class Order {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public Order(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createOrder(@Valid @RequestBody OrderDTO order) {
        String orderId = UUID.randomUUID().toString();
        OrderDTO orderDTO = new OrderDTO(
                orderId,
                order.getCustomerId(),
                order.getCreatedAt(),
                order.getOrderStatus(),
                order.getPaymentMethod(),
                order.getTotal(),
                order.getBarcode()
        );
        CreateOrderCommand command = new CreateOrderCommand(orderId, orderDTO);
        return commandGateway.send(command);
    }

    @PostMapping("/{orderId}/add-product")
    public CompletableFuture<String> addProductToOrder(@PathVariable String orderId, @Valid @RequestBody OrderLineDTO orderLine) {
        String orderLineId = UUID.randomUUID().toString();
      OrderLineDTO orderLineDTO = new OrderLineDTO(
        orderLineId,
        orderId,
        orderLine.getProductId(),
        orderLine.getQty()
      );
        AddProductToOrderCommand command = new AddProductToOrderCommand(orderId, orderLineDTO);
        return commandGateway.send(command);
    }

    @PutMapping("/{orderId}/confirm")
    public CompletableFuture<String> confirmOrder(@PathVariable String orderId) {
        ConfirmOrderCommand command = new ConfirmOrderCommand(orderId);
        return commandGateway.send(command);
    }

    @PostMapping("/{orderId}/generate-invoice")
    public CompletableFuture<String> generateInvoice(@PathVariable String orderId, @Valid @RequestBody InvoiceDTO invoice) {
        GenerateInvoiceCommand command = new GenerateInvoiceCommand(orderId, invoice);
        return commandGateway.send(command);
    }

    @PutMapping("/invoice/{invoiceId}/pay")
    public CompletableFuture<String> payInvoice(@PathVariable String invoiceId) {
        PayInvoiceCommand command = new PayInvoiceCommand(invoiceId);
        return commandGateway.send(command);
    }

    @PutMapping("/{orderId}/start-shipping")
    public CompletableFuture<String> startShipping(@PathVariable String orderId) {
        StartShippingCommand command = new StartShippingCommand(orderId);
        return commandGateway.send(command);
    }

    @PutMapping("/{orderId}/deliver")
    public CompletableFuture<String> deliverOrder(@PathVariable String orderId) {
        DeliverOrderCommand command = new DeliverOrderCommand(orderId);
        return commandGateway.send(command);
    }

    @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("️ Error: " + exception.getMessage());
    }
}
