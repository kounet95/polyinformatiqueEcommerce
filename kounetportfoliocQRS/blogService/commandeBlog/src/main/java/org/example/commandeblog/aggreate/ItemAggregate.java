package org.example.commandeblog.aggreate;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commands.*;
import org.example.polyinformatiquecoreapi.event.*;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Slf4j
@Aggregate
@Getter
@Setter
public class ItemAggregate {

    @AggregateIdentifier
    private String id;
    private String content;
    private String urlMedia;
    private String title;
    private LocalDate createdAt;
    private String authorId;
    // ARTICLE (Post) specific
    private String domainId;
    private List<String> tagIds;
    private List<String> commentIds;
    // NEWS specific
    private String summary;

    // EVENT specific
    private String location;
    private LocalDateTime   begin;
    private LocalDateTime  end;
    private boolean deleted;
    public ItemAggregate() {
        // Required by Axon
    }

    @CommandHandler
    public ItemAggregate(CreatePostCommand command) {
        log.info(" Handling CreatePostCommand for id: {}", command.getId());
        apply(new PostCreatedEvent(command.getId(), command.getArticleDTO()));
    }

    @CommandHandler
    public ItemAggregate(CreateNewsCommand command) {
        log.info(" Handling CreateNewsCommand for id: {}", command.getId());
        apply(new NewsCreatedEvent(command.getId(), command.getNewsDTO()));
    }

    @CommandHandler
    public ItemAggregate(CreateEventCommand command) {
        log.info(" Handling CreateEventCommand for id: {}", command.getId());
        apply(new EventCreatedEvent(command.getId(), command.getEventDTO()));
    }

    @EventSourcingHandler
    public void on(PostCreatedEvent event) {
        log.info(" Applying PostCreatedEvent for id: {}", event.getId());
        this.id = event.getId();
        this.content=event.getArticleDTO().getContent();
        this.urlMedia = event.getArticleDTO().getUrlMedia();
        this.title = event.getArticleDTO().getTitle();
        this.createdAt = event.getArticleDTO().getCreatedAt();
        this.authorId =event.getArticleDTO().getAuthorId();
        this.domainId = event.getArticleDTO().getDomainId();
        this.tagIds = event.getArticleDTO().getTagIds();
        this.commentIds=event.getArticleDTO().getCommentIds();





        log.debug(" Post created with title: '{}' and domain ID: {}", title, domainId);
    }

    @EventSourcingHandler
    public void on(NewsCreatedEvent event) {
        log.info(" Applying NewsCreatedEvent for id: {}", event.getId());
        this.id = event.getId();
        this.summary = event.getNewsDTO().getSummary();
        this.content=event.getNewsDTO().getContent();
        this.urlMedia = event.getNewsDTO().getUrlMedia();
        this.title = event.getNewsDTO().getTitle();
        this.createdAt = event.getNewsDTO().getCreatedAt();
        this.authorId = event.getNewsDTO().getAuthorId();
        this.domainId = event.getNewsDTO().getDomainId();
        this.tagIds = event.getNewsDTO().getTagIds();
        this.commentIds=event.getNewsDTO().getCommentIds();


        log.debug(" News created with title: '{}' and summary: {}", title, summary);
    }

    @EventSourcingHandler
    public void on(EventCreatedEvent event) {
        log.info("Applying EventCreatedEvent for id: {}", event.getId());
        this.id = event.getId();
        this.location = event.getEventDTO().getLocation();
        this.begin = event.getEventDTO().getBegin();
        this.end = event.getEventDTO().getEnd();
        this.content=event.getEventDTO().getContent();
        this.urlMedia = event.getEventDTO().getUrlMedia();
        this.title = event.getEventDTO().getTitle();
        this.createdAt = event.getEventDTO().getCreatedAt();
        this.authorId = event.getEventDTO().getAuthorId();
        this.domainId = event.getEventDTO().getDomainId();
        this.tagIds = event.getEventDTO().getTagIds();
        this.commentIds=event.getEventDTO().getCommentIds();


        log.debug(" Event created at location: '{}', from {} to {}", location, begin, end);
    }


    @CommandHandler
    public void handle(UpdatePostCommand command) {
        log.info("Handling UpdatePostCommand for id: {}", command.getId());
        apply(new PostUpdatedEvent(
                command.getArticle().getId(),
                command.getArticle().getTitle(),
                command.getArticle().getContent(),
                command.getArticle().getUrlMedia(),
                command.getArticle().getDomainId(),
                command.getArticle().getTagIds()
        ));
    }

    @EventSourcingHandler
    public void on(PostUpdatedEvent event) {
        log.info("Applying PostUpdatedEvent for id: {}", event.getId());
        this.title = event.getTitle();
        this.content = event.getContent();
        this.urlMedia = event.getUrlMedia();
        this.domainId = event.getDomainId();
        this.tagIds = event.getTagIds();
    }

    @CommandHandler
    public void handle(UpdateNewsCommand command) {
        log.info("Handling UpdateNewsCommand for id: {}", command.getId());
        apply(new NewsUpdatedEvent(
                command.getId(),
                command.getNewsDTO().getTitle(),
                command.getNewsDTO().getContent(),
                command.getNewsDTO().getUrlMedia(),
                command.getNewsDTO().getSummary()
        ));
    }
    @EventSourcingHandler
    public void on(NewsUpdatedEvent event) {
        log.info("Applying NewsUpdatedEvent for id: {}", event.getId());
        this.title = event.getTitle();
        this.content = event.getContent();
        this.urlMedia = event.getUrlMedia();
        this.summary = event.getSummary();
    }
    @CommandHandler
    public void handle(UpdateEventCommand command) {
        log.info("Handling UpdateEventCommand for id: {}", command.getId());
        apply(new EventUpdatedEvent(
                command.getId(),
                command.getEvent().getTitle(),
                command.getEvent().getContent(),
                command.getEvent().getUrlMedia(),
                command.getEvent().getLocation(),
                command.getEvent().getBegin(),
                command.getEvent().getEnd()
        ));
    }
    @EventSourcingHandler
    public void on(EventUpdatedEvent event) {
        log.info("Applying EventUpdatedEvent for id: {}", event.getId());
        this.title = event.getTitle();
        this.content = event.getContent();
        this.urlMedia = event.getUrlMedia();
        this.location = event.getLocation();
        this.begin = event.getBegin();
        this.end = event.getEnd();
    }
    @CommandHandler
    public void handle(DeleteItemCommand command) {
        log.info("Handling DeleteItemCommand for id: {}", command.getId());
        AggregateLifecycle.apply(new ItemDeletedEvent(command.getId()));
    }


    @EventSourcingHandler
    public void on(ItemDeletedEvent event) {
        log.info("Applying ItemDeletedEvent for id: {}", event.getId());
        this.id = event.getId();
        this.deleted = true;
    }



}
