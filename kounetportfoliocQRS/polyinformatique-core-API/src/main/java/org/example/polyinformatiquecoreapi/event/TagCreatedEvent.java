package org.example.polyinformatiquecoreapi.event;

import org.example.polyinformatiquecoreapi.dto.TagDTO;

import java.io.Serializable;

public class TagCreatedEvent extends BaseEvent<String> implements Serializable {
    private final TagDTO payload;

    public TagCreatedEvent(String id, TagDTO payload) {
        super(id);
        this.payload = payload;
    }

    public TagDTO getPayload() {
        return payload;
    }
}

class TagDeletedEvent extends BaseEvent<String> implements Serializable {
    public TagDeletedEvent(String id) {
        super(id);
    }
}
