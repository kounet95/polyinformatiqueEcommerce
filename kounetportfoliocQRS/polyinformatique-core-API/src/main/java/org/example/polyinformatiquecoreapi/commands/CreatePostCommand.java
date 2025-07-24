package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.ArticleDTO;

public class CreatePostCommand extends BaseCommand<String> {
    private final ArticleDTO articleDTO;
    public CreatePostCommand(String id,ArticleDTO articleDTO) {
        super(id);
        this.articleDTO = articleDTO;
    }

    public ArticleDTO getArticleDTO() {
        return articleDTO;
    }
}
