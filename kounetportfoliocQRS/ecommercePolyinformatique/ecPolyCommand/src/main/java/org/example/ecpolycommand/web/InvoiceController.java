package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCustomerCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteInvoiceCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.GenerateInvoiceCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/invoice/command")
public class InvoiceController {

  private final CommandGateway commandGateway;
  private final EventStore eventStore;

  public InvoiceController(CommandGateway commandGateway, EventStore eventStore) {
    this.commandGateway = commandGateway;
    this.eventStore = eventStore;
  }

  @PostMapping("/create")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> createInvoice(@Valid @RequestBody InvoiceDTO invoice) {
    String invoiceId = UUID.randomUUID().toString();

    InvoiceDTO invoiceDTO = new InvoiceDTO(
      invoiceId,
      invoice.getOrderId(),
      invoice.getCustumerId(),
      invoice.getAmount(),
      invoice.getMethodePayment(),
      invoice.getRestMonthlyPayment(),
      invoice.getPaymentStatus(),
      invoice.getSupplierId()
    );
    GenerateInvoiceCommand command = new GenerateInvoiceCommand(invoiceId, invoiceDTO);
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
  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> deleteCustomer(@PathVariable String id) {
    return commandGateway.send(new DeleteInvoiceCommand(id));
  }
}
