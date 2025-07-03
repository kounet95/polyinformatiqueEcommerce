package org.example.ecpolycommand.web;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.ecpolycommand.service.StripeService;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/order/command")
public class Order {

  private final CommandGateway commandGateway;
  private final EventStore eventStore;
  private final StripeService stripeService;

  public Order(CommandGateway commandGateway, EventStore eventStore, StripeService stripeService) {
    this.commandGateway = commandGateway;
    this.eventStore = eventStore;
    this.stripeService = stripeService;
  }

  @PostMapping("/create")
  public CompletableFuture<String> createOrder(@Valid @RequestBody OrderDTO order) {
    String orderId = UUID.randomUUID().toString();
    OrderDTO orderDTO = new OrderDTO(
      orderId,
      order.getCustomerId(),
      order.getSupplierId(),
      order.getCreatedAt(),
      order.getPaymentMethod(),
      order.getTotal(),
      order.getShippingId()
    );
    CreateOrderCommand command = new CreateOrderCommand(orderId, orderDTO);
    return commandGateway.send(command);
  }

  @PostMapping("/{orderId}/add-product")
  public CompletableFuture<String> addProductToOrder(@PathVariable String orderId, @Valid @RequestBody OrderLineDTO orderLine) {
    String orderLineId = UUID.randomUUID().toString();
    OrderLineDTO orderLineDTO = new OrderLineDTO(orderLineId, orderId, orderLine.getStockId(), orderLine.getQty());
    AddProductToOrderCommand command = new AddProductToOrderCommand(orderId, orderLineDTO);
    return commandGateway.send(command);
  }

  @PutMapping("/{orderId}/confirm")
  public CompletableFuture<String> confirmOrder(@PathVariable String orderId) {
    return commandGateway.send(new ConfirmOrderCommand(orderId));
  }

  @PostMapping("/{orderId}/generate-invoice")
  public CompletableFuture<String> generateInvoice(@PathVariable String orderId, @Valid @RequestBody InvoiceDTO invoice) {
    return commandGateway.send(new GenerateInvoiceCommand(orderId, invoice));
  }

  @PutMapping("/invoice/{invoiceId}/pay")
  public CompletableFuture<String> payInvoice(@PathVariable String invoiceId) {
    return commandGateway.send(new PayInvoiceCommand(invoiceId));
  }

  @PutMapping("/{orderId}/start-shipping")
  public CompletableFuture<String> startShipping(@PathVariable String orderId) {
    return commandGateway.send(new StartShippingCommand(orderId));
  }

  @PutMapping("/{orderId}/deliver")
  public CompletableFuture<String> deliverOrder(@PathVariable String orderId) {
    return commandGateway.send(new DeliverOrderCommand(orderId));
  }

  @PostMapping("/payment-intent")
  public ResponseEntity<String> createPaymentIntent(@RequestBody OrderDTO order) {
    try {
      PaymentIntent paymentIntent = stripeService.createPaymentIntent(order.getTotal());
      return ResponseEntity.ok(paymentIntent.getClientSecret());
    } catch (StripeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur Stripe : " + e.getMessage());
    }
  }

  @DeleteMapping("/{orderId}")
  public CompletableFuture<String> cancelOrder(@PathVariable String orderId, @RequestParam(defaultValue = "Cancelled by user") String reason) {
    return commandGateway.send(new CancelOrderCommand(orderId, reason));
  }

  @GetMapping("/events/{aggregateId}")
  public Stream<?> eventsStream(@PathVariable String aggregateId) {
    return eventStore.readEvents(aggregateId).asStream();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> exceptionHandler(Exception exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("️ Error: " + exception.getMessage());
  }
}
