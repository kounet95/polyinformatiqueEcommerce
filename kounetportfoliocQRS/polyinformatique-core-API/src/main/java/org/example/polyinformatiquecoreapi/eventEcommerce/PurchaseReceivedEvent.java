package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.PurchaseDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class PurchaseReceivedEvent extends BaseEvent<String> {
    private final PurchaseDTO purchaseDTO;

    public PurchaseReceivedEvent(String id, PurchaseDTO purchaseDTO) {
        super(id);
        this.purchaseDTO = purchaseDTO;
    }

    public PurchaseDTO getPurchaseDTO() {
        return purchaseDTO;
    }
}

