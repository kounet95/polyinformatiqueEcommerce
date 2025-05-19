package org.example.queryblog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;

import org.example.polyinformatiquecoreapi.event.EventCreatedEvent;
import org.example.polyinformatiquecoreapi.event.EventUpdatedEvent;
import org.example.polyinformatiquecoreapi.event.ItemDeletedEvent;
import org.example.queryblog.entite.*;
import org.example.queryblog.repos.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventService {

    private final DomainRepository domainRepository;
    private final UtilisateurRepos utilisateurRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;

    @EventHandler
    public void on(EventCreatedEvent event) {
        log.debug("Handling EventCreatedEvent: {}", event.getId());

        // On récupère le domain et l'utilisateur par leur ID
        Domain domain = domainRepository.findById(event.getEventDTO().getDomainId())
                .orElseThrow(() -> new RuntimeException("Domain not found"));

        Customer utilisateur = utilisateurRepository.findById(event.getEventDTO().getAuthorId())
                .orElseThrow(() -> new RuntimeException("Utilisateur not found"));

        // Recuperer les IDs de commentaires envoyes dans la commande
        List<String> idsCommentaires = event.getEventDTO().getCommentIds();

        // Recuperer les commentaires qui correspondent à ces IDs dans la base
        List<Comment> commentaires = commentRepository.findAllById(idsCommentaires);

        // Recuperer les IDs de commentaires envoyes dans la commande
        List<String> idsTags = event.getEventDTO().getTagIds();

        // Recuperer les commentaires qui correspondent à ces IDs dans la base
        List<Tag> tags = tagRepository.findAllById(idsTags);

        //creation d'un event dans la base de donne
        Event eventEtite = Event.builder()
                .createdAt(event.getEventDTO().getCreatedAt())
                .begin(event.getEventDTO().getBegin())
                .end(event.getEventDTO().getEnd())
                .comments(commentaires)
                .tags(tags)
                .utilisateur(utilisateur)
                .title(event.getEventDTO().getTitle())
                .content(event.getEventDTO().getContent())
                .location(event.getEventDTO().getLocation())
                .build();
        eventRepository.save(eventEtite);
    }

    @EventHandler
    public void on(EventUpdatedEvent event) {
        log.debug("Handling EventUpdatedEvent: {}", event.getId());

        Event eventEntity = eventRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventEntity.setTitle(event.getTitle());
        eventEntity.setContent(event.getContent());
        eventEntity.setUrlMedia(event.getUrlMedia());
        eventEntity.setLocation(event.getLocation());
        eventEntity.setBegin(event.getBegin());
        eventEntity.setEnd(event.getEnd());

        eventRepository.save(eventEntity);
    }

    @EventHandler
    public void on(ItemDeletedEvent event) {
        log.debug("Handling ItemDeletedEvent: {}", event.getId());

        eventRepository.deleteById(event.getId());
    }
}
