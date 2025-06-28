package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;
@Getter
@Builder
@AllArgsConstructor
public class CreateAddressCommand{
  private String id;
  private AddressDTO addressDTO;


}
