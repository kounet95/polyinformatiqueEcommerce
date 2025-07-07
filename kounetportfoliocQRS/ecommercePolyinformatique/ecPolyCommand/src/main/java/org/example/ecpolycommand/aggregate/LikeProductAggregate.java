package org.example.ecpolycommand.aggregate;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.DelikerProductCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.LikerProductCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.LikeDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductLikedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductdeLikedEvent;

import java.time.LocalDateTime;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
public class LikeProductAggregate {
@AggregateIdentifier
  private String id;
  private String  user;
  private String product;
  private LocalDateTime createdAt;

  public LikeProductAggregate() {}

  @CommandHandler
  public void handler(LikerProductCommand cmd) {
    apply(new ProductLikedEvent(cmd.getId(), cmd.getLikeDTO()));
  }

  @EventSourcingHandler
  public void on(ProductLikedEvent event) {
    this.id=event.getId();
    this.user=event.getLikeDTO().getUser();
    this.product=event.getLikeDTO().getProduct();
    this.createdAt=LocalDateTime.now();
  }
  @CommandHandler
  public void handler(DelikerProductCommand cmd){
    apply(new ProductdeLikedEvent(cmd.getId()));
  }

  @EventSourcingHandler
  public void  on(ProductdeLikedEvent event){
    this.id="";
    this.user="";
    this.product="";
    this.createdAt=LocalDateTime.now();
  }

}
