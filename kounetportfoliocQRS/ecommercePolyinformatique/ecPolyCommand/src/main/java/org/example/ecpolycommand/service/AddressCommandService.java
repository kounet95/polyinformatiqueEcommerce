package org.example.ecpolycommand.service;

import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;

public interface AddressCommandService {

  void createAddress(AddressDTO dto);
  void updateAddress(String id, AddressDTO dto);
  void deleteAddress(String id);
}
