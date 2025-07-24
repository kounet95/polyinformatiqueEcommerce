package org.example.queryblog.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;

import org.example.polyinformatiquecoreapi.event.AuthorCreatedEvent;
import org.example.polyinformatiquecoreapi.event.AuthorUpdateEvent;
import org.example.polyinformatiquecoreapi.event.ItemDeletedEvent;


import org.example.queryblog.entite.Comment;
import org.example.queryblog.entite.Customer;
import org.example.queryblog.entite.Item;
import org.example.queryblog.repos.CommentRepository;
import org.example.queryblog.repos.IteamRepository;
import org.example.queryblog.repos.UtilisateurRepos;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerService {

    private final UtilisateurRepos utilisateurRepos;
    private final IteamRepository teamRepo;
    private final CommentRepository commentRepo;

    @EventHandler
    public void on(AuthorCreatedEvent event) {
        log.debug("Handling CustomerCreatedEvent: {}", event.getId());

        List<Item> itemList = teamRepo.findAllById(event.getAuthor().getIteamIds());
        List<Comment> commentList = commentRepo.findAllById(event.getAuthor().getCommentIds());
        Customer utilisateurs = Customer.builder()
                .username(event.getAuthor().getUsername())
                .phone(event.getAuthor().getPhone())
                .address(event.getAuthor().getAddress())
                .name(event.getAuthor().getName())
                .commentList(commentList)
                .items(itemList)
                .build();

        utilisateurRepos.save(utilisateurs);
    }

    @EventHandler
    public void on(ItemDeletedEvent event) {
        log.debug("Handling ItemDeletedEvent: {}", event.getId());

        utilisateurRepos.deleteById(event.getId());
    }



    @EventHandler
    public void on(AuthorUpdateEvent event) {
        log.debug("Handling AuthorUpdatedEvent: {}", event.getId());

        Customer utilisateur = utilisateurRepos.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur not found with id: " + event.getId()));

        utilisateur.setUsername(event.getAuthor().getUsername());
        utilisateur.setPhone(event.getAuthor().getPhone());
        utilisateur.setAddress(event.getAuthor().getAddress());
        utilisateur.setName(event.getAuthor().getName());

        // Mise à jour des commentaires
        List<Comment> commentList = commentRepo.findAllById(event.getAuthor().getCommentIds());
        utilisateur.setCommentList(commentList);

        // Mise à jour des items
        List<Item> itemList = teamRepo.findAllById(event.getAuthor().getIteamIds());
        utilisateur.setItems(itemList);
        utilisateurRepos.save(utilisateur);
        log.info("Utilisateur updated: {}", utilisateur.getId());
    }

}
