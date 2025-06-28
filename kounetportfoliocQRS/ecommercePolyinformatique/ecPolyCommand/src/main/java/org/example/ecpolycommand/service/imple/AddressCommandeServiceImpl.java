package org.example.ecpolycommand.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.ecpolycommand.service.AddressCommandService;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;

import java.util.UUID;


public class AddressCommandeServiceImpl implements AddressCommandService {


  private final CommandGateway commandGateway;

  public AddressCommandeServiceImpl(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @Override
  public void createAddress(AddressDTO dto) {
    String id = UUID.randomUUID().toString();
    commandGateway.sendAndWait(new CreateAddressCommand(id, dto));
  }

  @Override
  public void updateAddress(String id, AddressDTO dto) {
    commandGateway.sendAndWait(new UpdateAddressCommand(id, dto));
  }

  @Override
  public void deleteAddress(String id) {

    commandGateway.sendAndWait(new DeleteAddressCommand(id));
  }

}
