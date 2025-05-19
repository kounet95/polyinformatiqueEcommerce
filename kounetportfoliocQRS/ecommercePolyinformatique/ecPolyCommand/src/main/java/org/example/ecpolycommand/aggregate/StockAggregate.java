package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.AddStockCommand;
import org.example.polyinformatiquecoreapi.eventEcommerce.StockDecreasedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.StockIncreasedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@Getter
@Setter
public class StockAggregate {

    @AggregateIdentifier
    private String productId;
    private int quantity;

    public StockAggregate() {}

    @CommandHandler
    public StockAggregate(AddStockCommand cmd) {
        apply(new StockIncreasedEvent(cmd.getId(), cmd.getStockDTO()));
    }

    @EventSourcingHandler
    public void on(StockIncreasedEvent event) {
        this.productId = event.getId();
        this.quantity += event.getStockDTO().getQuantity();
    }

    @EventSourcingHandler
    public void on(StockDecreasedEvent event) {
        this.quantity -= event.getStockDTO().getQuantity();
    }
}



