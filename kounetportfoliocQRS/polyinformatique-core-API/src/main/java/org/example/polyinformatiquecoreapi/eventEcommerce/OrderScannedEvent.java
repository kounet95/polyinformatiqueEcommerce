package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

@Getter
public class OrderScannedEvent extends BaseEvent<String> {
    private final String barcode;

    public OrderScannedEvent(String id, String barcode) {
        super(id);
        this.barcode = barcode;
    }
}