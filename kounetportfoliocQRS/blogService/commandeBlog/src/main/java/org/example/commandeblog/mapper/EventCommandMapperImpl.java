package org.example.commandeblog.mapper;

import org.example.commandeblog.aggreate.ItemAggregate;
import org.example.polyinformatiquecoreapi.dto.EventDTO;
import org.springframework.stereotype.Service;

@Service
public class EventCommandMapperImpl {

    public ItemAggregate toAggregate(EventDTO dto) {
        if (dto == null) return null;
        ItemAggregate aggregate = new ItemAggregate();
        aggregate.setId(dto.getId());
        aggregate.setLocation(dto.getLocation());
        aggregate.setBegin(dto.getBegin());
        aggregate.setEnd(dto.getEnd());
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

    public EventDTO toDTO(ItemAggregate aggregate) {
        if (aggregate == null) return null;
        return new EventDTO(
            aggregate.getId(),
            aggregate.getLocation(),
            aggregate.getBegin(),
            aggregate.getEnd(),
            aggregate.getContent(),
            aggregate.getUrlMedia(),
            aggregate.getTitle(),
            aggregate.getCreatedAt(),
            aggregate.getAuthorId(),
            aggregate.getDomainId(),
            aggregate.getTagIds(),
            aggregate.getCommentIds()
        );
    }
}