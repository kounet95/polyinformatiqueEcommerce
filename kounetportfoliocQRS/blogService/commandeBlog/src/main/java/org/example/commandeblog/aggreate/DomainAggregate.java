package org.example.commandeblog.aggreate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import org.example.polyinformatiquecoreapi.commands.CreateDomainCommand;
import org.example.polyinformatiquecoreapi.commands.DeleteDomainCommand;
import org.example.polyinformatiquecoreapi.event.DomainCreateEvent;
import org.example.polyinformatiquecoreapi.event.DomainDeletedEvent;

import java.util.List;

@Aggregate
@Slf4j
@Getter
@Setter
public class DomainAggregate {
    @AggregateIdentifier
    private String id;

    private String name;
    private List<String> articles;

    public DomainAggregate() {}

    @CommandHandler
    public DomainAggregate(CreateDomainCommand command) {
        //log.info("Handling AddCategoryCommand for id: {}", command.getId());
        DomainCreateEvent event = new DomainCreateEvent(command.getId(), command.getPayload());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(DomainCreateEvent event) {
        this.id = event.getId();
        this.name = event.getDomainDTO().getName();
        this.articles = event.getDomainDTO().getArticles();
    }

    @CommandHandler
    public void handle(DeleteDomainCommand command) {
        log.info("Handling DeleteDomainCommand for id: {}", command.getId());
        AggregateLifecycle.apply(new DomainDeletedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(DomainDeletedEvent event) {
        log.info("Applying DomainDeletedEvent for id: {}", event.getId());
        // Mark the domain as deleted or perform any necessary cleanup
        this.name = "[deleted]";
        this.articles = null;
    }
}
