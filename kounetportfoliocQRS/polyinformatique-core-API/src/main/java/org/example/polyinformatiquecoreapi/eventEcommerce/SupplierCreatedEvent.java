package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class SupplierCreatedEvent  extends BaseEvent<String> {

    private SupplierDTO supplierDTO;

    public SupplierCreatedEvent(String id, SupplierDTO supplierDTO) {
        super(id);
        this.supplierDTO = supplierDTO;
    }
    public SupplierDTO getSupplierDTO() {
        return supplierDTO;
    }


}
