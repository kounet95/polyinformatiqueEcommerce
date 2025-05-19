package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.PurchaseDTO;

public class ReceivePurchaseCommand extends BaseCommand<String> {
    private final PurchaseDTO purchaseDTO;

    public ReceivePurchaseCommand(String id, PurchaseDTO purchaseDTO) {
        super(id);
        this.purchaseDTO = purchaseDTO;
    }

    public PurchaseDTO getPurchaseDTO() {
        return purchaseDTO;
    }
}