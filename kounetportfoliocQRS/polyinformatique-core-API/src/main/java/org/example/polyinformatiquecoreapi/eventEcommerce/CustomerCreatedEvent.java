package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

import java.io.Serializable;

public class CustomerCreatedEvent extends BaseEvent<String> implements Serializable {

    private CustomerEcommerceDTO author;
    public CustomerCreatedEvent(String id, CustomerEcommerceDTO author) {
        super(id);
        this.author = author;
    }

    public CustomerEcommerceDTO getAuthor() {
        return author;
    }
}
