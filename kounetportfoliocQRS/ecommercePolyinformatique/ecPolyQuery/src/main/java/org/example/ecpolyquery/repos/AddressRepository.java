package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.AddressLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,String> {
  @Query("SELECT a FROM Address a JOIN a.links l WHERE l.targetType = 'CUSTOMER' AND l.targetId = :customerId")
  List<Address> findAllByCustomerId(@Param("customerId") String customerId);

}
