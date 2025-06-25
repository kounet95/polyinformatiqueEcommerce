package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;
@Getter
public class CreateAddressCommand extends BaseCommand<String>{

  private AddressDTO addressDTO;

  public CreateAddressCommand(String id, AddressDTO addressDTO) {
    super(id);
    this.addressDTO = addressDTO;
  }
}
