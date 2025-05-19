package org.example.ecpolycommand.aggregate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.ReceivePurchaseCommand;
import org.example.polyinformatiquecoreapi.eventEcommerce.PurchaseReceivedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


/**
 * Scénario 4 : Réception d’un stock fournisseur
 * Commande : ReceivePurchase(purchaseId)
 *   => Événement : PurchaseReceived(purchaseId, items)
 *   => Événement : StockIncreased(productId, size, qty)
 * Projection : ProductStockView, SupplierInventoryView
 */
@Aggregate
@Slf4j
@Getter
@Setter
public class PurchaseAggregate {

    @AggregateIdentifier
    private String purchaseId;

    private String supplierId;

    private String createdAt;


    private String status;


    private double total;

    public PurchaseAggregate() {}

    @CommandHandler
    public PurchaseAggregate(ReceivePurchaseCommand cmd) {
        apply(new PurchaseReceivedEvent(cmd.getId(), cmd.getPurchaseDTO()));
    }

    @EventSourcingHandler
    public void on(PurchaseReceivedEvent event) {
        this.purchaseId = event.getId();
    }
}