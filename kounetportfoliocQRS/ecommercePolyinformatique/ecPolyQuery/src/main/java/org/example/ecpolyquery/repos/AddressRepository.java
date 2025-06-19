package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,String> {

}
