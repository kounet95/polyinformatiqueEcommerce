package org.example.queryblog.mapper;

import org.example.polyinformatiquecoreapi.dto.DomainDTO;
import org.example.queryblog.entite.Article;
import org.example.queryblog.entite.Domain;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DomainMapper {
    DomainDTO toDTO(Domain domain);

    default List<String> map(List<Article> articles) {
        if (articles == null) {
            return null;
        }
        return articles.stream()
                .map(Article::getId)
                .collect(Collectors.toList());
    }
}
