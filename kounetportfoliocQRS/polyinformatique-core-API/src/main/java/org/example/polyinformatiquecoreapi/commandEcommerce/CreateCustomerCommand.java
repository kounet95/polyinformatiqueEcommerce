package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.commands.BaseCommand;
import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;

public class CreateCustomerCommand extends BaseCommand<String> {

    private final CustomerEcommerceDTO author;


    public CreateCustomerCommand(String id, CustomerEcommerceDTO author) {
        super(id);
        this.author = author;
    }

    public CustomerEcommerceDTO getAuthor() {
        return author;
    }
}
