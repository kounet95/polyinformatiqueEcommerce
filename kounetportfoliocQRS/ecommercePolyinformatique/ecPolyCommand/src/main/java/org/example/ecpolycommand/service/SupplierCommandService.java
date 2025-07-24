package org.example.ecpolycommand.service;

import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;

public interface SupplierCommandService {
    void createSupplier(SupplierDTO dto);
    void deleteSupplier(String id);
}