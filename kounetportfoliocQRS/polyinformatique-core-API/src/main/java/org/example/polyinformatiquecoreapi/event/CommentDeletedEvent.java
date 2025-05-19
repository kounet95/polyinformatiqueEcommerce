package org.example.polyinformatiquecoreapi.event;

import java.io.Serializable;

public class CommentDeletedEvent extends BaseEvent<String> implements Serializable {
    private final String commentId;

    public CommentDeletedEvent(String id, String commentId) {
        super(id);
        this.commentId = commentId;
    }

    public String getCommentId() {
        return commentId;
    }


 }
