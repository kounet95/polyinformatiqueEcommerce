package org.example.queryblog.web;


import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.example.polyinformatiquecoreapi.dto.ArticleDTO;
import org.example.polyinformatiquecoreapi.query.WatchArticleQuery;

import org.example.queryblog.query.GetAllArticlesQuery;
import org.example.queryblog.query.GetArticleByIdQuery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query")
@AllArgsConstructor
public class ArticleQueryController {
    private final QueryGateway queryGateway;

    @GetMapping("/articles")
    public CompletableFuture<List<ArticleDTO>> getAllArticle() {
        System.out.println(">>> Sending GetAllArticlesQuery");
        return queryGateway.query(new GetAllArticlesQuery(),
                ResponseTypes.multipleInstancesOf(ArticleDTO.class));
    }

    @GetMapping("/articles/{id}")
    public CompletableFuture<ArticleDTO> getArticleById(@PathVariable String id) {
        System.out.println(">>> Sending GetArticleByIdQuery for id: " + id);
        return queryGateway.query(new GetArticleByIdQuery(id),
                ResponseTypes.instanceOf(ArticleDTO.class));
    }

    @GetMapping(value = "/watch/{articleId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ArticleDTO> watchArticle(@PathVariable String articleId) {
        System.out.println(">>> Sending WatchArticleQuery for id: " + articleId);
        SubscriptionQueryResult<ArticleDTO, ArticleDTO> result = queryGateway.subscriptionQuery(
                new WatchArticleQuery(articleId),
                ResponseTypes.instanceOf(ArticleDTO.class),
                ResponseTypes.instanceOf(ArticleDTO.class)
        );
        return result.initialResult().concatWith(result.updates());
    }
}
