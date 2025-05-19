package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;

public class UpdateAuthorCommand extends BaseCommand<String> {
    private final CustomerBlogDTO author;

    public UpdateAuthorCommand(String id, CustomerBlogDTO author) {
        super(id);
        this.author = author;
    }

    public CustomerBlogDTO getAuthor() {
        return author;
    }
}
