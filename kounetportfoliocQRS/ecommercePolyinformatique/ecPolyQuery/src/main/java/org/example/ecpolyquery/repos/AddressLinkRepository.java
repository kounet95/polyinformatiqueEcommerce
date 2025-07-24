package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.AddressLink;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressLinkRepository extends JpaRepository<AddressLink, String> {
  /**
   * Récupère tous les liens pour un targetType + targetId.
   * Exemple : targetType="CUSTOMER", targetId="123"
   */
  List<AddressLink> findByTargetTypeAndTargetId(String targetType, String targetId);

  /**
   * Récupère tous les liens associés à une Address précise (par son ID).
   * Utile pour vérifier qui pointe vers une adresse donnée.
   */
  List<AddressLink> findByAddress_Id(String addressId);

  /**
   * Supprime tous les liens associés à une Address donnée.
   * On préfère utiliser Address plutôt que String pour que Spring Data gère bien la jointure.
   */
  void deleteByAddress(Address address);

}


