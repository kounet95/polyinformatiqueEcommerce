package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Data;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

import java.io.Serializable;
@Data
public class CustomerCreatedEvent  {

    private CustomerEcommerceDTO author;
    public CustomerCreatedEvent(CustomerEcommerceDTO author) {
        this.author = author;
    }

    public CustomerEcommerceDTO getAuthor() {
        return author;
    }
}
