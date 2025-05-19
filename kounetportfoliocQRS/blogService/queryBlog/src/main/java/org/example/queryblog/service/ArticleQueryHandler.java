package org.example.queryblog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.example.polyinformatiquecoreapi.dto.ArticleDTO;
import org.example.polyinformatiquecoreapi.event.PostCreatedEvent;
import org.example.polyinformatiquecoreapi.event.PostUpdatedEvent;
import org.example.polyinformatiquecoreapi.query.WatchArticleQuery;
import org.example.queryblog.entite.Article;
import org.example.queryblog.mapper.ArticleMapper;
import org.example.queryblog.query.GetAllArticlesQuery;
import org.example.queryblog.query.GetArticleByIdQuery;
import org.example.queryblog.repos.ArticleRepository;
import org.example.queryblog.repos.DomainRepository;
import org.example.queryblog.repos.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ArticleQueryHandler {

    private final ArticleRepository articleRepository;
    private final DomainRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ArticleMapper articleMapper;
    private final QueryUpdateEmitter queryUpdateEmitter;

    @QueryHandler
    public List<ArticleDTO> on(GetAllArticlesQuery query) {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryHandler
    public ArticleDTO on(GetArticleByIdQuery query) {
        Optional<Article> optionalArticle = articleRepository.findById(query.getId());
        return optionalArticle
                .map(articleMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + query.getId()));
    }

    @QueryHandler
    public ArticleDTO on(WatchArticleQuery query) {
        log.debug("Handling WatchArticleQuery: {}", query.getArticleId());
        Optional<Article> optionalArticle = articleRepository.findById(query.getArticleId());
        return optionalArticle
                .map(articleMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + query.getArticleId()));
    }

    @EventHandler
    public void on(PostCreatedEvent event) {
        log.debug("Handling PostCreatedEvent for subscription queries: {}", event.getId());
        // The event already contains the ArticleDTO
        ArticleDTO articleDTO = event.getArticleDTO();

        // Emit update to subscribers watching this article
        queryUpdateEmitter.emit(WatchArticleQuery.class,
                query -> query.getArticleId().equals(event.getId()),
                articleDTO);
    }

    @EventHandler
    public void on(PostUpdatedEvent event) {
        log.debug("Handling PostUpdatedEvent for subscription queries: {}", event.getId());

        // Find the article and convert to DTO
        Optional<Article> optionalArticle = articleRepository.findById(event.getId());
        if (optionalArticle.isPresent()) {
            ArticleDTO articleDTO = articleMapper.toDTO(optionalArticle.get());

            // Emit update to subscribers watching this article
            queryUpdateEmitter.emit(WatchArticleQuery.class,
                    query -> query.getArticleId().equals(event.getId()),
                    articleDTO);
        }
    }
}
