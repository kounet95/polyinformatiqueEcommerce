package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.ShippingDTO;
import org.example.ecpolycommand.aggregate.ShippingAggregate;
import org.springframework.stereotype.Component;

@Component
public class ShippingMapper {

    public ShippingAggregate toAggregate(ShippingDTO dto) {
        ShippingAggregate agg = new ShippingAggregate();
        agg.setShippingId(dto.getId());
        agg.setOrderId(dto.getOrderId());
        agg.setAddress(dto.getShippingAddress());
        agg.setStatus(dto.getDeliveryStatus());
        agg.setTrackingNumber(dto.getEstimatedDeliveryDate());
        agg.setCarrier(dto.getShippingDate());
        return agg;
    }

    public ShippingDTO toDTO(ShippingAggregate agg) {
        return new ShippingDTO(
            agg.getShippingId(),
            agg.getOrderId(),
            agg.getAddress(),
            agg.getStatus(),
            agg.getTrackingNumber(),
            agg.getCarrier()
        );
    }
}