package org.example.commandeblog.mapper;

import org.example.commandeblog.aggreate.ItemAggregate;
import org.example.polyinformatiquecoreapi.dto.ArticleDTO;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommandMapper {

    public ItemAggregate toAggregate(ArticleDTO dto) {
        if (dto == null) return null;
        ItemAggregate aggregate = new ItemAggregate();
        aggregate.setId(dto.getId());
        aggregate.setTitle(dto.getTitle());
        aggregate.setContent(dto.getContent());
        aggregate.setAuthorId(dto.getAuthorId());
        aggregate.setCreatedAt(dto.getCreatedAt());
        aggregate.setCommentIds(dto.getCommentIds());
        aggregate.setUrlMedia(dto.getUrlMedia());
        aggregate.setTagIds(dto.getTagIds());
        aggregate.setDomainId(dto.getDomainId());
        return aggregate;
    }

    public ArticleDTO toDTO(ItemAggregate aggregate) {
        if (aggregate == null) return null;
        ArticleDTO dto = new ArticleDTO();
        dto.setId(aggregate.getId());
        dto.setTitle(aggregate.getTitle());
        dto.setContent(aggregate.getContent());
        dto.setAuthorId(aggregate.getAuthorId());
        dto.setCreatedAt(aggregate.getCreatedAt());
        dto.setCommentIds(aggregate.getCommentIds());
        dto.setUrlMedia(aggregate.getUrlMedia());
        dto.setTagIds(aggregate.getTagIds());
        dto.setDomainId(aggregate.getDomainId());
        return dto;
    }
}