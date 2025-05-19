package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;

/**
 * Event emitted when a product is deleted
 */
@Getter
public class ProductDeletedEvent {
    private final String id;

    public ProductDeletedEvent(String id) {
        this.id = id;
    }
}