package org.example.commandeblog.aggreate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commands.CreateMediaCommand;
import org.example.polyinformatiquecoreapi.commands.DeleteMediaCommand;
import org.example.polyinformatiquecoreapi.dto.MediaDTO;
import org.example.polyinformatiquecoreapi.event.MediaCreatedEvent;
import org.example.polyinformatiquecoreapi.event.MediaDeletedEvent;

import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@Getter
@Setter
public class MediaAggregate {

    @AggregateIdentifier
    private String id;
   private String type;
    private String url;
    private String fileName;
    private String fileType;
    private String ItemId;

    public MediaAggregate() {

    }

    @CommandHandler
    public MediaAggregate(CreateMediaCommand command) {
        log.info("Handling CreateMediaCommand: {}", command.getId());
        // Just pass the command payload to the event
        apply(new MediaCreatedEvent(command.getId(), command.getPayload()));
    }

    @EventSourcingHandler
    public void on(MediaCreatedEvent event) {
        log.info("Applying MediaCreatedEvent: {}", event.getId());
        this.id = event.getId();
        this.type = event.getPayload().getFileType();
        this.url = event.getPayload().getUrl();
        this.fileName = event.getPayload().getFileName();
        this.fileType = event.getPayload().getFileType();
        this.ItemId = event.getPayload().getItemId();

    }

    @CommandHandler
    public void handle(DeleteMediaCommand command) {
        log.info("Handling DeleteMediaCommand: {}", command.getId());
        apply(new MediaDeletedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(MediaDeletedEvent event) {
        log.info("Applying MediaDeletedEvent: {}", event.getId());
    }
}
