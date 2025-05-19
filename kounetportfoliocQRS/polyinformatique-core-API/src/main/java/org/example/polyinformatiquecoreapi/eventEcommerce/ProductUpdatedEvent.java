package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

import java.io.Serializable;

public class ProductUpdatedEvent extends BaseEvent<String> implements Serializable {
    private ProductDTO productDTO;

    public ProductUpdatedEvent(String id, ProductDTO productDTO) {
        super(id);
        this.productDTO = productDTO;
    }
    
    public ProductDTO getProductDTO() {
        return productDTO;
    }
}