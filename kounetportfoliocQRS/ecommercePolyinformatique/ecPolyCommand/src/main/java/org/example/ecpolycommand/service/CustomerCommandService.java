package org.example.ecpolycommand.service;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;

public interface CustomerCommandService {
    void createCustomer(CustomerEcommerceDTO dto);
    void updateCustomer(String id, CustomerEcommerceDTO dto);
    void deleteCustomer(String id);
}