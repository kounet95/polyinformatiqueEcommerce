package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.AddressLink;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address,String> {

}
