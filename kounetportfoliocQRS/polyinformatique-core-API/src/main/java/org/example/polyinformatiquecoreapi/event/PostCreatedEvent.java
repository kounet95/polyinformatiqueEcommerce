package org.example.polyinformatiquecoreapi.event;



import org.example.polyinformatiquecoreapi.dto.ArticleDTO;

import java.io.Serializable;
import java.time.Instant;

import java.time.LocalDate;
import java.util.List;

public class PostCreatedEvent extends BaseEvent<String> {

    private final ArticleDTO articleDTO;

    public PostCreatedEvent(
            String id, ArticleDTO articleDTO

            ) {
        super(id);

        this.articleDTO = articleDTO;
    }


    public ArticleDTO getArticleDTO() {
        return articleDTO;
    }
}
