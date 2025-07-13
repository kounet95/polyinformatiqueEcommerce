package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.GenerateInvoiceCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteInvoiceCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.InvoiceGeneratedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.InvoiceDeletedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@Getter
@Setter
public class InvoiceAggregate {

  @AggregateIdentifier
  private String invoiceId;
  private String orderId;
  private String customerId;
  private double amount;
  private String paymentMethod;
  private Double restMonthlyPayment;
  private String paymentStatus;
  private String supplierId;
  private boolean deleted = false;

  public InvoiceAggregate() {}

  @CommandHandler
  public InvoiceAggregate(GenerateInvoiceCommand cmd) {
    apply(new InvoiceGeneratedEvent(cmd.getId(), cmd.getInvoiceDTO()));
  }

  @EventSourcingHandler
  public void on(InvoiceGeneratedEvent event) {
    InvoiceDTO dto = event.getInvoiceDTO();
    this.invoiceId = event.getId();
    this.orderId = dto.getOrderId();
    this.customerId = dto.getCustumerEmal();
    this.amount = dto.getAmount();
    this.paymentMethod = dto.getMethodePayment();
    this.restMonthlyPayment = dto.getRestMonthlyPayment();
    this.paymentStatus = dto.getPaymentStatus();
    this.supplierId = dto.getSupplierId();
    this.deleted = false;
  }

  @CommandHandler
  public void handle(DeleteInvoiceCommand cmd) {
    if (this.deleted) {
      throw new IllegalStateException("Invoice already deleted!");
    }
    apply(new InvoiceDeletedEvent(cmd.getId()));
  }

  @EventSourcingHandler
  public void on(InvoiceDeletedEvent event) {
    this.deleted = true;
  }
}
