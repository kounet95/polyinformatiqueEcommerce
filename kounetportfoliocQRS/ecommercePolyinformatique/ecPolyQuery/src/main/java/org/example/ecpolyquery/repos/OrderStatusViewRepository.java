package org.example.ecpolyquery.repos;

import org.example.ecpolyquery.entity.OrderStatusView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderStatusViewRepository extends JpaRepository<OrderStatusView, String> {
    Optional<OrderStatusView> findByBarcode(String barcode);
    Optional<OrderStatusView> findByOrderId(String orderId);
}