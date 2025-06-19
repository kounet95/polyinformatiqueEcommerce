package org.example.ecpolycommand.mapper;

import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;
import org.example.ecpolycommand.aggregate.SupplierAggregate;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierAggregate toAggregate(SupplierDTO dto) {
        SupplierAggregate agg = new SupplierAggregate();
        agg.setSupplierId(dto.getId());
        agg.setFirstName(dto.getFullname());
        agg.setEmail(dto.getEmail());
        agg.setAddressId(dto.getAddressId());

        agg.setPersonToContact(dto.getPersonToContact());
        return agg;
    }

    public SupplierDTO toDTO(SupplierAggregate agg) {
        return new SupplierDTO(
            agg.getSupplierId(),
            agg.getFirstName(),
            agg.getEmail(),
            agg.getPersonToContact(),
            agg.getAddressId()
        );
    }
}
