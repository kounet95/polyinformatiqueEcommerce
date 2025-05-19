package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;

public class CreateCustomerCommand extends BaseCommand<String>{

    private CustomerBlogDTO author;


    public CreateCustomerCommand(String id, CustomerBlogDTO author) {
        super(id);
        this.author = author;
    }

    public CustomerBlogDTO getAuthor() {
        return author;
    }
}
