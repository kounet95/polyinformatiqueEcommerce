package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.ArticleDTO;

import java.util.List;

public class UpdatePostCommand extends BaseCommand<String> {
  private final ArticleDTO article;

    public UpdatePostCommand(
            String id, ArticleDTO article

            ) {
        super(id);

        this.article = article;
    }

    public ArticleDTO getArticle() {
        return article;
    }
}
