package org.example.commandeblog.mapper;

import org.example.commandeblog.aggreate.CommentAggregate;
import org.example.polyinformatiquecoreapi.dto.CommentDTO;
import org.springframework.stereotype.Service;

@Service
public class CommentCommandMapperImpl {

    public CommentAggregate toAggregate(CommentDTO dto) {
        if (dto == null) return null;
        CommentAggregate aggregate = new CommentAggregate();
        aggregate.setIdComment(dto.getId());
        aggregate.setContenu(dto.getContenu());
        aggregate.setCreatedAt(dto.getCreatedAt());
        aggregate.setAuthorId(dto.getAuthorId());
        aggregate.setPostId(dto.getItemId());
        return aggregate;
    }

    public CommentDTO toDTO(CommentAggregate aggregate) {
        if (aggregate == null) return null;
        return new CommentDTO(
            aggregate.getIdComment(),
            aggregate.getContenu(),
            aggregate.getCreatedAt(),
            aggregate.getAuthorId(),
            aggregate.getPostId()
        );
    }
}