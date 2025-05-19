package org.example.polyinformatiquecoreapi.event;

import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;

public class AuthorUpdateEvent {

    private final String id;
    private final CustomerBlogDTO author;

    public AuthorUpdateEvent(String id, CustomerBlogDTO author) {
        this.id = id;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public CustomerBlogDTO getAuthor() {
        return author;
    }
}
