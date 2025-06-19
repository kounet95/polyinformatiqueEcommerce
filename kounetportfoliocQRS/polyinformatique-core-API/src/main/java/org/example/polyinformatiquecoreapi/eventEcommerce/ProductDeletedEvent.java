package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import lombok.Setter;

/**
 * Event emitted when a product is deleted
 */
@Getter @Setter
public class ProductDeletedEvent {
    private final String id;

    public ProductDeletedEvent(String id) {
        this.id = id;
    }
}
