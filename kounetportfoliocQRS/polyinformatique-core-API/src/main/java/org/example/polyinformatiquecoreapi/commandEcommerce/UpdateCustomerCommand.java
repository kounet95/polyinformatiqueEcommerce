package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;

public class UpdateCustomerCommand extends BaseCommand<String> {
    private final CustomerEcommerceDTO customerDTO;

    public UpdateCustomerCommand(String id, CustomerEcommerceDTO customerDTO) {
        super(id);
        this.customerDTO = customerDTO;
    }

    public CustomerEcommerceDTO getCustomerDTO() {
        return customerDTO;
    }
}