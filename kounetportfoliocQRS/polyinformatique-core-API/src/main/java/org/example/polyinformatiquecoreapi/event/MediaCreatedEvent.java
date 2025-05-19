package org.example.polyinformatiquecoreapi.event;

import org.example.polyinformatiquecoreapi.dto.MediaDTO;

import java.io.Serializable;

public class MediaCreatedEvent extends BaseEvent<String> implements Serializable {
    private final MediaDTO payload;

    public MediaCreatedEvent(String id, MediaDTO payload) {
        super(id);
        this.payload = payload;
    }

    public MediaDTO getPayload() {
        return payload;
    }
}
