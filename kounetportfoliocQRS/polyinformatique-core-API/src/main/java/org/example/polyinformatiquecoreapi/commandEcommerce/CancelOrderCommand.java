package org.example.polyinformatiquecoreapi.commandEcommerce;

public class CancelOrderCommand extends BaseCommand<String> {
    private final String reason;

    public CancelOrderCommand(String id, String reason) {
        super(id);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}