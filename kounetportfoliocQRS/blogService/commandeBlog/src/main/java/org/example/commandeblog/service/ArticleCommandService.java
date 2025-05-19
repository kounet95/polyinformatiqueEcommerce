package org.example.commandeblog.service;

import org.example.polyinformatiquecoreapi.dto.ArticleDTO;

public interface ArticleCommandService {
    void createArticle(ArticleDTO dto);
    void updateArticle(String id, ArticleDTO dto);
    void deleteArticle(String id);
}