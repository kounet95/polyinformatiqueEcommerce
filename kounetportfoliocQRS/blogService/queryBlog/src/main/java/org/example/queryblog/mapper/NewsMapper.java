package org.example.queryblog.mapper;

import org.example.polyinformatiquecoreapi.dto.NewsDTO;
import org.example.queryblog.entite.News;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsDTO toDTO(News news);
}