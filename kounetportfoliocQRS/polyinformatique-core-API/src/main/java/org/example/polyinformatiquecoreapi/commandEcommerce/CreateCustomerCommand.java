package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Builder;
import org.example.polyinformatiquecoreapi.commands.BaseCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
@Builder
public class CreateCustomerCommand {

    private final CustomerEcommerceDTO author;


    public CreateCustomerCommand(CustomerEcommerceDTO author) {
        this.author = author;
    }

    public CustomerEcommerceDTO getAuthor() {
        return author;
    }
}
