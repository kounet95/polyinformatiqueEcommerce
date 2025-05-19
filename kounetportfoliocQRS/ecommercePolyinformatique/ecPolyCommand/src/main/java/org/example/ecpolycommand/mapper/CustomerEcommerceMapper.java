package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.example.ecpolycommand.aggregate.CustomerAggregate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerEcommerceMapper {

    public CustomerAggregate toAggregate(CustomerEcommerceDTO dto) {
        CustomerAggregate agg = new CustomerAggregate();
        agg.setCustomerId(dto.getId());
        agg.setFirstName(dto.getFirstname());
        agg.setLastName(dto.getLastname());
        agg.setEmail(dto.getEmail());
        agg.setPhone(dto.getPhone());
        agg.setShippingAddress(dto.getShippingAddress());
        agg.setBillingAddress(dto.getBillingAddress());
        agg.setAddress(dto.getShippingAddress()); 
        return agg;
    }

    public CustomerEcommerceDTO toDTO(CustomerAggregate aggregate) {
        return new CustomerEcommerceDTO(
            aggregate.getCustomerId(),
            aggregate.getFirstName(),
            aggregate.getLastName(),
            aggregate.getEmail(),
            aggregate.getPhone(),
            aggregate.getShippingAddress(),
            aggregate.getBillingAddress(),
            LocalDateTime.now() 
        );
    }
}