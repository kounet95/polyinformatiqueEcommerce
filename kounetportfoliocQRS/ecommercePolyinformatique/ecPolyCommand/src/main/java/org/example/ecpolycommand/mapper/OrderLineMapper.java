package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;
import org.example.ecpolycommand.aggregate.OrderLineAggregate;
import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {

    public OrderLineAggregate toAggregate(OrderLineDTO dto) {
        OrderLineAggregate agg = new OrderLineAggregate();
        agg.setOrderLineId(dto.getId());
        agg.setOrderId(dto.getOrderId());
        agg.setProductId(dto.getProductId());
        agg.setQty(dto.getQty());
        return agg;
    }

    public OrderLineDTO toDTO(OrderLineAggregate agg) {
        return new OrderLineDTO(
            agg.getOrderLineId(),
            agg.getOrderId(),
            agg.getProductId(),
            agg.getQty()
        );
    }
}
