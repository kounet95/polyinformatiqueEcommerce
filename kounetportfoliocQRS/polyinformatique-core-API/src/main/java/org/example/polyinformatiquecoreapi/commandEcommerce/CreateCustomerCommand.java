package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.commands.BaseCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;

public class CreateCustomerCommand {

    private final CustomerEcommerceDTO author;


    public CreateCustomerCommand(CustomerEcommerceDTO author) {
        this.author = author;
    }

    public CustomerEcommerceDTO getAuthor() {
        return author;
    }
}
