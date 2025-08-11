package org.example.polyinformatiquecoreapi.commandEcommerce;

public class CancelOrderCommand extends BaseCommand<String> {
    private String reason = "";

    public CancelOrderCommand(String id) {
        super(id);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
