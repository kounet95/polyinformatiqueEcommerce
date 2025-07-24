package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.ShippingDTO;

public class CreateShippingCommand extends BaseCommand<String> {
    private final ShippingDTO shippingDTO;

    public CreateShippingCommand(String id, ShippingDTO shippingDTO) {
        super(id);
        this.shippingDTO = shippingDTO;
    }

    public ShippingDTO getShippingDTO() {
        return shippingDTO;
    }
}