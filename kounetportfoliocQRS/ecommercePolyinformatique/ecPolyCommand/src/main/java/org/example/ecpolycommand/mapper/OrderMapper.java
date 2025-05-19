package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.example.ecpolycommand.aggregate.OrderAggregate;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderAggregate toAggregate(OrderDTO dto) {
        OrderAggregate agg = new OrderAggregate();
        agg.setOrderId(dto.getId());
        agg.setOrderStatus(dto.getOrderStatus());
        agg.setBarcode(dto.getBarcode());
        // Les champs confirmed, paid, shipped, delivered sont gérés par les événements, pas le DTO
        // Les orderLines sont gérés par des commandes séparées (AddProductToOrder)
        return agg;
    }

    public OrderDTO toDTO(OrderAggregate agg) {
        return new OrderDTO(
            agg.getOrderId(),
            null, // customerId non présent dans l'aggregate
            null, // createdAt non présent dans l'aggregate
            agg.getOrderStatus(),
            null, // paymentMethod non présent dans l'aggregate
            0,    // total non présent dans l'aggregate
            agg.getBarcode()
        );
    }
}