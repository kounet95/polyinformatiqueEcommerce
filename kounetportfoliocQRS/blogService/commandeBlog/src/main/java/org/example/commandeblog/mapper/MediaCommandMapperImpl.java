package org.example.commandeblog.mapper;

import org.example.commandeblog.aggreate.MediaAggregate;
import org.example.polyinformatiquecoreapi.dto.MediaDTO;
import org.springframework.stereotype.Service;

@Service
public class MediaCommandMapperImpl {

    public MediaAggregate toAggregate(MediaDTO dto) {
        if (dto == null) return null;
        MediaAggregate aggregate = new MediaAggregate();
        aggregate.setId(dto.getId());
        aggregate.setUrl(dto.getUrl());
        aggregate.setFileName(dto.getFileName());
        aggregate.setFileType(dto.getFileType());
        aggregate.setItemId(dto.getItemId());
        return aggregate;
    }

    public MediaDTO toDTO(MediaAggregate aggregate) {
        if (aggregate == null) return null;
        return new MediaDTO(
            aggregate.getId(),
            aggregate.getUrl(),
            aggregate.getFileName(),
            aggregate.getFileType(),
            aggregate.getItemId()
        );
    }
}