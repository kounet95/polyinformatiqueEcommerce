package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;

@Getter
public class ScanOrderCommand extends BaseCommand<String> {
    private final String barcode;

    public ScanOrderCommand(String id, String barcode) {
        super(id);
        this.barcode = barcode;
    }
}