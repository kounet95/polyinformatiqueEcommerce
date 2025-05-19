package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;

@Getter
public class UpdateOrderStatusCommand extends BaseCommand<String> {
    private final String barcode;
    private final String newStatus;

    public UpdateOrderStatusCommand(String id, String barcode, String newStatus) {
        super(id);
        this.barcode = barcode;
        this.newStatus = newStatus;
    }
}