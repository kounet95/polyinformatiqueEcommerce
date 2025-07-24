package org.example.commandeblog.aggreate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commands.CreateTagCommand;
import org.example.polyinformatiquecoreapi.event.TagCreatedEvent;

import java.util.List;

@Aggregate
@Slf4j
@Getter
@Setter
public class TagAggregate {
    @AggregateIdentifier
    private String id;

    private String title;
    private List<String> itemIds;

    public TagAggregate() {}

    @CommandHandler
    public TagAggregate(CreateTagCommand command) {
        log.info("Handling AddTagCommand for id: {}", command.getId());
        TagCreatedEvent event = new TagCreatedEvent(command.getId(), command.getPayload());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(TagCreatedEvent event) {
        this.id = event.getId();
        this.title = event.getPayload().getName();
        this.itemIds = event.getPayload().getItemIds();
    }
}