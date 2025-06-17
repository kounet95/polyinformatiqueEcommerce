package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateSocialGroupCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteSocialGroupCommand;
import org.example.polyinformatiquecoreapi.eventEcommerce.SocialGroupCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.SocialGroupDeletedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Getter
@Setter
public class SocialGroupAggregate {

  @AggregateIdentifier
  private String socialGroupId;
  private String name;
  private String region;
  private String country;
  private String pays;
  private boolean deleted;

  public SocialGroupAggregate() {}

  @CommandHandler
  public SocialGroupAggregate(CreateSocialGroupCommand cmd) {
    apply(new SocialGroupCreatedEvent(cmd.getId(), cmd.getSocialGroupDTO()));
  }

  @CommandHandler
  public void handle(DeleteSocialGroupCommand cmd) {
    if (this.deleted) {
      throw new IllegalStateException("SocialGroup already deleted.");
    }
    apply(new SocialGroupDeletedEvent(cmd.getId()));
  }

  @EventSourcingHandler
  public void on(SocialGroupCreatedEvent event) {
    this.socialGroupId = event.getId();
    this.name = event.getSocialGroupDTO().getName();
    this.region = event.getSocialGroupDTO().getRegion();
    this.country = event.getSocialGroupDTO().getCountry();
    this.pays = event.getSocialGroupDTO().getPays();
    this.deleted = false;
  }

  @EventSourcingHandler
  public void on(SocialGroupDeletedEvent event) {
    this.deleted = true;
  }
}
