package org.example.polyinformatiquecoreapi.commandEcommerce;

/**
 * Command to delete a product
 */
public class DeleteProductCommand extends BaseCommand<String> {

    public DeleteProductCommand(String id) {
        super(id);
    }
}