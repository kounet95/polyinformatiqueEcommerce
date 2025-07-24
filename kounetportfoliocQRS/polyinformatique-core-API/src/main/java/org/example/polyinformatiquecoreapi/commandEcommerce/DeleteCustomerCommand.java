package org.example.polyinformatiquecoreapi.commandEcommerce;

/**
 * Command to delete a customer
 */
public class DeleteCustomerCommand extends BaseCommand<String> {

    public DeleteCustomerCommand(String id) {
        super(id);
    }
}