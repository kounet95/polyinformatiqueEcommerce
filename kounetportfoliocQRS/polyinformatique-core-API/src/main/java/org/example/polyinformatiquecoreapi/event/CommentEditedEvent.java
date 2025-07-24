package org.example.polyinformatiquecoreapi.event;

import java.io.Serializable;
import java.time.Instant;

public class CommentEditedEvent extends BaseEvent<String> implements Serializable {

    private final String newContent;

    private final String oldContent;

    private final Instant editedAt;

    public CommentEditedEvent(String id, String newContent, String oldContent) {
        super(id);

        this.newContent = newContent;
        this.oldContent = oldContent;
        this.editedAt = Instant.now();
    }


    public String getNewContent() {
        return newContent;
    }

    public Instant getEditedAt() {
        return editedAt;
    }

    public String getOldContent() {
        return oldContent;
    }
}
