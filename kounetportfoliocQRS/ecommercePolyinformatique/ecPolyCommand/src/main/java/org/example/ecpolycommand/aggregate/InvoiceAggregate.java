package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateInvoiceCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.InvoiceCreatedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Invoice Aggregate for handling invoice-related commands
 */
@Aggregate
@Slf4j
@Getter
@Setter
public class InvoiceAggregate {

    @AggregateIdentifier
    private String invoiceId;
    private String orderId;
    private double amount;
    private String paymentStatus;

    public InvoiceAggregate() {}


     @CommandHandler
     public InvoiceAggregate(CreateInvoiceCommand cmd) {
         apply(new InvoiceCreatedEvent(cmd.getId(), cmd.getInvoiceDTO()));
     }


     @EventSourcingHandler
     public void on(InvoiceCreatedEvent event) {
         this.invoiceId = event.getId();
         this.orderId = event.getInvoiceDTO().getOrderId();
         this.amount = event.getInvoiceDTO().getAmount();
         this.paymentStatus = event.getInvoiceDTO().getPaymentStatus();
     }
}
