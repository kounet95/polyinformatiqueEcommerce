package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

import java.io.Serializable;

public class CustomerUpdatedEvent extends BaseEvent<String> implements Serializable {
    private CustomerEcommerceDTO customerDTO;

    public CustomerUpdatedEvent(String id, CustomerEcommerceDTO customerDTO) {
        super(id);
        this.customerDTO = customerDTO;
    }
    
    public CustomerEcommerceDTO getCustomerDTO() {
        return customerDTO;
    }
}