package org.example.commandeblog.service;

import org.example.polyinformatiquecoreapi.dto.NewsDTO;

public interface NewsCommandService {
    void createNews(NewsDTO dto);
    void updateNews(String id, NewsDTO dto);
    void deleteNews(String id);
}