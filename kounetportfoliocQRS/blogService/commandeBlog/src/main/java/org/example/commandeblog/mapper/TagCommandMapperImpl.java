package org.example.commandeblog.mapper;

import org.example.commandeblog.aggreate.TagAggregate;
import org.example.polyinformatiquecoreapi.dto.TagDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagCommandMapperImpl {

    public TagAggregate toAggregate(TagDTO dto) {
        if (dto == null) return null;
        TagAggregate aggregate = new TagAggregate();
        aggregate.setId(dto.getId());
        aggregate.setTitle(dto.getName());
        aggregate.setItemIds(dto.getItemIds());
        return aggregate;
    }

    public TagDTO toDTO(TagAggregate aggregate) {
        if (aggregate == null) return null;
        return new TagDTO(
            aggregate.getId(),
            aggregate.getTitle(),
            aggregate.getItemIds()
        );
    }
}