package org.example.ecpolycommand.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.ecpolycommand.service.CustomerCommandService;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCustomerCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.UpdateCustomerCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCustomerCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommandServiceImpl implements CustomerCommandService {

    private final CommandGateway commandGateway;

    public CustomerCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createCustomer(CustomerEcommerceDTO dto) {
        commandGateway.sendAndWait(new CreateCustomerCommand(dto.getId(), dto));
    }

    @Override
    public void updateCustomer(String id, CustomerEcommerceDTO dto) {
        commandGateway.sendAndWait(new UpdateCustomerCommand(id, dto));
    }

    @Override
    public void deleteCustomer(String id) {
        commandGateway.sendAndWait(new DeleteCustomerCommand(id));
    }
}