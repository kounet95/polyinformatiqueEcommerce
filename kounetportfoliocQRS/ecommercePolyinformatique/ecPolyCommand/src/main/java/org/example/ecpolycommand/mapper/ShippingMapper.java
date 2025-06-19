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
    agg.setAddress(dto.getShippingAddressId());
    agg.setOrderStatus(dto.getOrderStatus());
    agg.setEstimatedDeliveryDate(dto.getEstimatedDeliveryDate());
    agg.setShippingDate(dto.getShippingDate());
    agg.setCreatedAt(dto.getCreatedAt());
    return agg;
  }

  public ShippingDTO toDTO(ShippingAggregate agg) {
    return new ShippingDTO(
      agg.getShippingId(),
      agg.getOrderId(),
      agg.getEstimatedDeliveryDate(),
      agg.getShippingDate(),
      agg.getCreatedAt(),
      agg.getAddress(),
      agg.getOrderStatus()
    );
  }
}
