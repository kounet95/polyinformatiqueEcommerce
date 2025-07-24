package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateSupplierCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateSupplyCommande;
import org.example.polyinformatiquecoreapi.eventEcommerce.CreatedSupplyEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.SupplierCreatedEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@Getter
@Setter
public class SupplyAggregate {
  @AggregateIdentifier
  private String id;
  private String name;
  private LocalDateTime createdAt;
  private List<String> stocksIds;

  public SupplyAggregate(){}


  @CommandHandler
  public SupplyAggregate(CreateSupplyCommande cmd) {
    String generatedId = UUID.randomUUID().toString();
    this.id = generatedId;
    apply(new CreatedSupplyEvent(generatedId, cmd.getSupplyDTO()));
  }

  @EventSourcingHandler
  public void on(CreatedSupplyEvent event) {

    this.id = event.getId();
    this.name=event.getSupplyDTO().getName();
    this.createdAt = event.getSupplyDTO().getCreatedAt();
    this.stocksIds = event.getSupplyDTO().getStocksIds();
  }

}
