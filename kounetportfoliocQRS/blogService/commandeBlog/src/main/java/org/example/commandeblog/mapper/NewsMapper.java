package org.example.commandeblog.mapper;

import org.example.commandeblog.aggreate.ItemAggregate;
import org.example.polyinformatiquecoreapi.dto.NewsDTO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class NewsMapper {
    public ItemAggregate toAggregate(NewsDTO dto) {
        if (dto == null) return null;
        ItemAggregate aggregate = new ItemAggregate();
        aggregate.setId(dto.getId());
        aggregate.setSummary(dto.getSummary());
        aggregate.setContent(dto.getContent());
        aggregate.setUrlMedia(dto.getUrlMedia());
        aggregate.setTitle(dto.getTitle());
        aggregate.setCreatedAt(dto.getCreatedAt());
        aggregate.setAuthorId(dto.getAuthorId());
        aggregate.setDomainId(dto.getDomainId());
        aggregate.setTagIds(dto.getTagIds());
        aggregate.setCommentIds(dto.getCommentIds());
        return aggregate;
    }

    public NewsDTO toDTO(ItemAggregate aggregate) {
        if (aggregate == null) return null;
        NewsDTO dto = new NewsDTO(
                aggregate.getId(),
                aggregate.getSummary(),
                aggregate.getContent(),
                aggregate.getUrlMedia(),
                aggregate.getTitle(),
                aggregate.getCreatedAt(),
                aggregate.getAuthorId(),
                aggregate.getDomainId(),
                aggregate.getTagIds(),
                aggregate.getCommentIds()
        );
        return dto;
    }
}