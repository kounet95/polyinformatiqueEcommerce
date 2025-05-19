package org.example.polyinformatiquecoreapi.event;

import java.io.Serializable;

public class AuthorDeleteEvent extends BaseEvent<String> implements Serializable {
    public AuthorDeleteEvent(String id) {
        super(id);
    }
}
