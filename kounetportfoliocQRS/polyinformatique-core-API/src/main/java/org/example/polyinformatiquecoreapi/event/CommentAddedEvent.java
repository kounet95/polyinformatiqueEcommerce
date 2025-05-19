package org.example.polyinformatiquecoreapi.event;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dto.CommentDTO;

import java.io.Serializable;
import java.time.Instant;

public class CommentAddedEvent extends BaseEvent<String> implements Serializable {
    private final CommentDTO commentDTO;

    public CommentAddedEvent(String id,  CommentDTO commentDTO) {
        super(id);
        this.commentDTO = commentDTO;

    }


    public CommentDTO getCommentDTO() {
        return commentDTO;
    }
}


