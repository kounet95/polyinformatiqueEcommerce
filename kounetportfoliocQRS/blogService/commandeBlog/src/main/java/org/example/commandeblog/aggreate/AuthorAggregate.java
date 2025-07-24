package org.example.commandeblog.aggreate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commands.CreateCustomerCommand;
import org.example.polyinformatiquecoreapi.commands.DeleteAuthorCommand;
import org.example.polyinformatiquecoreapi.commands.UpdateAuthorCommand;
import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;
import org.example.polyinformatiquecoreapi.event.AuthorCreatedEvent;
import org.example.polyinformatiquecoreapi.event.AuthorDeleteEvent;
import org.example.polyinformatiquecoreapi.event.AuthorUpdateEvent;

import java.util.List;
@Aggregate
@Slf4j
@Getter @Setter
public class AuthorAggregate {
    @AggregateIdentifier
    private String authorId;
    private String username;
    private String lastname;
    private String email;
    private String name;
    private String phone;
    private String address;

    private List<String> commentIds;
    private List<String> itemIds;

    public AuthorAggregate() {}
    @CommandHandler
    public AuthorAggregate(CreateCustomerCommand command) {
        CustomerBlogDTO authorDTO = command.getAuthor();
        AuthorCreatedEvent authorCreatedEvent = new AuthorCreatedEvent(authorDTO.getId(),authorDTO);
        AggregateLifecycle.apply(authorCreatedEvent);
    }

    @EventSourcingHandler
    public void on(AuthorCreatedEvent event) {
        this.authorId = event.getId();
        this.username = event.getAuthor().getUsername();
        this.lastname = event.getAuthor().getLastname();
        this.email = event.getAuthor().getEmail();
        this.name = event.getAuthor().getName();
        this.phone = event.getAuthor().getPhone();
        this.address = event.getAuthor().getAddress();
        this.commentIds = event.getAuthor().getCommentIds();
        this.itemIds = event.getAuthor().getIteamIds();
    }

    @CommandHandler
    public void handle(UpdateAuthorCommand command) {
        log.info("Handling UpdateAuthorCommand for id: {}", command.getId());
        AggregateLifecycle.apply(new AuthorUpdateEvent(command.getId(), command.getAuthor()));
    }

    @EventSourcingHandler
    public void on(AuthorUpdateEvent event) {
        log.info("Applying AuthorUpdateEvent for id: {}", event.getId());
        CustomerBlogDTO author = event.getAuthor();
        this.username = author.getUsername();
        this.lastname = author.getLastname();
        this.email = author.getEmail();
        this.name = author.getName();
        this.phone = author.getPhone();
        this.address = author.getAddress();
    }

    @CommandHandler
    public void handle(DeleteAuthorCommand command) {
        log.info("Handling DeleteAuthorCommand for id: {}", command.getId());
        AggregateLifecycle.apply(new AuthorDeleteEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(AuthorDeleteEvent event) {
        log.info("Applying AuthorDeleteEvent for id: {}", event.getId());
        // Mark the author as deleted or perform any necessary cleanup
        this.username = "[deleted]";
        this.lastname = "[deleted]";
        this.email = null;
        this.name = "[deleted]";
        this.phone = null;
        this.address = null;
        this.commentIds = null;
        this.itemIds = null;
    }
}
