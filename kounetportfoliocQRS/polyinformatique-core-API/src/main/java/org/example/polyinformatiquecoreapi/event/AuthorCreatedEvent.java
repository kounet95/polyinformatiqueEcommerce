package org.example.polyinformatiquecoreapi.event;

import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;

import java.io.Serializable;

public class AuthorCreatedEvent extends BaseEvent<String> implements Serializable {

    private CustomerBlogDTO author;
    public AuthorCreatedEvent(String id, CustomerBlogDTO author) {
        super(id);
        this.author = author;
    }

    public CustomerBlogDTO getAuthor() {
        return author;
    }
}
