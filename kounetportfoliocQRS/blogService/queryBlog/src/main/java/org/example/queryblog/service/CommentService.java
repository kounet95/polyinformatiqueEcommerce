package org.example.queryblog.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;

import org.example.polyinformatiquecoreapi.event.CommentAddedEvent;
import org.example.polyinformatiquecoreapi.event.CommentEditedEvent;
import org.example.polyinformatiquecoreapi.event.CommentDeletedEvent;
import org.example.polyinformatiquecoreapi.event.ItemDeletedEvent;
import org.example.queryblog.entite.Comment;
import org.example.queryblog.entite.Customer;
import org.example.queryblog.entite.Item;
import org.example.queryblog.repos.CommentRepository;
import org.example.queryblog.repos.IteamRepository;
import org.example.queryblog.repos.UtilisateurRepos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UtilisateurRepos utilisateurRepos;
    private final IteamRepository teamRepo;


    @EventHandler
    public void on(CommentAddedEvent event) {
        log.debug("Handling CommentAddedEvent: {}", event.getId());

        // On récupère l'utilisateur par leur ID
        Customer utilisateur = utilisateurRepos.findById(event.getCommentDTO().getAuthorId())
                .orElseThrow(() -> new RuntimeException("Utilisateur not found"));
       Item item = teamRepo.findById(event.getCommentDTO().getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));
        Comment comment = Comment.builder()
                .item(item)
                .content(event.getCommentDTO().getContenu())
                .createdAt(event.getCommentDTO().getCreatedAt())
                .utilisateur(utilisateur)
                .build();
        commentRepository.save(comment);
    }

    @EventHandler
    public void on(CommentEditedEvent event) {
        log.debug("Handling CommentEditedEvent: {}", event.getId());

        Comment comment = commentRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(event.getNewContent());

        commentRepository.save(comment);
    }

    @EventHandler
    public void on(CommentDeletedEvent event) {
        log.debug("Handling CommentDeletedEvent: {}", event.getId());

        commentRepository.deleteById(event.getCommentId());
    }

    @EventHandler
    public void on(ItemDeletedEvent event) {
        log.debug("Handling ItemDeletedEvent: {}", event.getId());

        commentRepository.deleteById(event.getId());
    }
}
