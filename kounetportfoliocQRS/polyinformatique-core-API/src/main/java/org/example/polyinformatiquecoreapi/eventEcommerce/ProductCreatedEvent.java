package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;

/**
 * Event emitted when a product is created
 */
@Getter
public class ProductCreatedEvent {
    private final String id;
    private final ProductDTO productDTO;

    public ProductCreatedEvent(String id, ProductDTO productDTO) {
        this.id = id;
        this.productDTO = productDTO;
    }
}