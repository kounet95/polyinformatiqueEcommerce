package org.example.ecpolycommand.aggregate;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.DelikerProductCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.LikerProductCommand;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductLikedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductdeLikedEvent;

import java.time.LocalDateTime;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


@Aggregate
@Slf4j
public class LikeProductAggregate {

  @AggregateIdentifier
  private String id;

  private String user;
  private String product;
  private LocalDateTime createdAt;

  // Constructeur vide pour Axon
  public LikeProductAggregate() {}

  // ✅ Le "create" doit être un constructeur CommandHandler
  @CommandHandler
  public LikeProductAggregate(LikerProductCommand cmd) {
    log.info("Handling Like Command for product {} by user {}", cmd.getProduct(), cmd.getUser());
    apply(new ProductLikedEvent(cmd.getId(), cmd.getUser(), cmd.getProduct()));
  }

  @EventSourcingHandler
  public void on(ProductLikedEvent event) {
    this.id = event.getId();
    this.user = event.getUser();
    this.product = event.getProduct();
    this.createdAt = LocalDateTime.now();
  }

  @CommandHandler
  public void handle(DelikerProductCommand cmd) {
    log.info("Handling Unlike Command for Like ID {}", cmd.getId());
    apply(new ProductdeLikedEvent(cmd.getId()));
  }

  @EventSourcingHandler
  public void on(ProductdeLikedEvent event) {
    log.info("Handling Unlike event for Like ID {}", event.getId());
    org.axonframework.modelling.command.AggregateLifecycle.markDeleted();
  }

}
