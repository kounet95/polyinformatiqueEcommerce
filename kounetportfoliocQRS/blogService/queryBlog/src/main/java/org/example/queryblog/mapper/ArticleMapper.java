package org.example.queryblog.mapper;

import org.example.polyinformatiquecoreapi.dto.ArticleDTO;
import org.example.queryblog.entite.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleDTO toDTO(Article article);
}

