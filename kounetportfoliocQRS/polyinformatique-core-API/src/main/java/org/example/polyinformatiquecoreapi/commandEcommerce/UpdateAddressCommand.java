package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;

@Getter
public class UpdateAddressCommand extends BaseCommand<String>{
  private AddressDTO dto;

  public UpdateAddressCommand(String id, AddressDTO dto) {
    super(id);
    this.dto = dto;
  }
}
