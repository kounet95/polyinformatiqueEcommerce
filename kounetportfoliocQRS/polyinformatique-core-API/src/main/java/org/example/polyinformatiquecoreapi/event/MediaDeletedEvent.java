package org.example.polyinformatiquecoreapi.event;

import java.io.Serializable;

public class MediaDeletedEvent extends BaseEvent<String> implements Serializable {
    public MediaDeletedEvent(String id) {
        super(id);
    }
}