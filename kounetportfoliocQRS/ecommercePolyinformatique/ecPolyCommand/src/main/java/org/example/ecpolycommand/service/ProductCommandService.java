package org.example.ecpolycommand.service;

import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;

public interface ProductCommandService {
    void createProduct(ProductDTO dto);
    void updateProduct(String id, ProductDTO dto);
    void deleteProduct(String id);
}