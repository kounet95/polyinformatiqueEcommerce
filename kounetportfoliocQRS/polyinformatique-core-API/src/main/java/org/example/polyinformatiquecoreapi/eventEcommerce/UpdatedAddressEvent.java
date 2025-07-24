package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

import java.io.Serializable;

public class UpdatedAddressEvent extends BaseEvent<String> implements Serializable {
    private AddressDTO addressDTO;

    public UpdatedAddressEvent(String id, AddressDTO addressDTO) {
        super(id);
        this.addressDTO = addressDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }
}
