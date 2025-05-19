package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;

/**
 * Event emitted when a product size is created
 */
@Getter
public class ProductSizeCreatedEvent {
    private final String id;
    private final ProductSizeDTO productSizeDTO;

    public ProductSizeCreatedEvent(String id, ProductSizeDTO productSizeDTO) {
        this.id = id;
        this.productSizeDTO = productSizeDTO;
    }
}