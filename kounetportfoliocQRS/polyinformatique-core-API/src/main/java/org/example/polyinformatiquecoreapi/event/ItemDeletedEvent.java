package org.example.polyinformatiquecoreapi.event;


public class ItemDeletedEvent {
    private final String id;

    public ItemDeletedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
