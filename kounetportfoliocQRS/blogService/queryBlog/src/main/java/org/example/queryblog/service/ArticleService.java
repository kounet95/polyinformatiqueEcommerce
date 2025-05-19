package org.example.queryblog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;

import org.example.polyinformatiquecoreapi.event.*;
import org.example.queryblog.entite.*;
import org.example.queryblog.repos.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final DomainRepository domainRepository;
    private final UtilisateurRepos utilisateurRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;

    @EventHandler
    public void on(PostCreatedEvent event) {
        log.debug("Handling PostCreatedEvent: {}", event.getId());

        Domain domain = domainRepository.findById(event.getArticleDTO().getDomainId())
                .orElseThrow(() -> new RuntimeException("Domain not found"));

        Customer utilisateur = utilisateurRepository.findById(event.getArticleDTO().getAuthorId())
                .orElseThrow(() -> new RuntimeException("Utilisateur not found"));

        List<Comment> commentaires = commentRepository.findAllById(event.getArticleDTO().getCommentIds());
        List<Tag> tags = tagRepository.findAllById(event.getArticleDTO().getTagIds());

        Article article = Article.builder()
                .title(event.getArticleDTO().getTitle())
                .content(event.getArticleDTO().getContent())
                .urlMedia(event.getArticleDTO().getUrlMedia())
                .createdAt(LocalDate.now())
                .utilisateur(utilisateur)
                .comments(commentaires)
                .tags(tags)
                .domain(domain)
                .build();

        articleRepository.save(article);
    }

    @EventHandler
    public void on(PostUpdatedEvent event) {
        log.debug("Handling PostUpdatedEvent: {}", event.getId());

        Article article = articleRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Article not found"));

        Domain domain = domainRepository.findById(event.getDomainId())
                .orElseThrow(() -> new RuntimeException("Domain not found"));

        List<Tag> tags = tagRepository.findAllById(event.getTagIds());

        article.setTitle(event.getTitle());
        article.setContent(event.getContent());
        article.setUrlMedia(event.getUrlMedia());
        article.setDomain(domain);
        article.setTags(tags);

        articleRepository.save(article);
    }

    @EventHandler
    public void on(ItemDeletedEvent event) {
        log.debug("Handling ItemDeletedEvent: {}", event.getId());

        articleRepository.deleteById(event.getId());
    }
}
