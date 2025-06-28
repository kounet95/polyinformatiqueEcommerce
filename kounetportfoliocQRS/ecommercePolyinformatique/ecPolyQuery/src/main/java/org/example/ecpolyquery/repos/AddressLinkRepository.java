package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.AddressLink;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressLinkRepository extends JpaRepository<AddressLink, Long> {
  List<AddressLink> findByTargetTypeAndTargetId(String targetType, String targetId);
  List<AddressLink> findByAddress_Id(String addressId);
}
