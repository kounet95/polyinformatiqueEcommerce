package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, String> {
}