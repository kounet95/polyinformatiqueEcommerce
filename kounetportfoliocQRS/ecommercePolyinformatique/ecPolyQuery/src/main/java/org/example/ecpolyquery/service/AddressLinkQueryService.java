package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.AddressLink;
import org.example.ecpolyquery.repos.AddressLinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressLinkQueryService {

  private final AddressLinkRepository addressLinkRepository;

  // Récupération les adresses pour n'importe quel type d'entité
  public List<Address> getAddressesFor(String targetType, String targetId) {
    List<AddressLink> links = addressLinkRepository.findByTargetTypeAndTargetId(targetType, targetId);
    return links.stream().map(AddressLink::getAddress).toList();
  }
}
