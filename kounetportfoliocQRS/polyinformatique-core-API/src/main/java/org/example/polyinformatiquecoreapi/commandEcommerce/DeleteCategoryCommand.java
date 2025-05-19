package org.example.polyinformatiquecoreapi.commandEcommerce;

/**
 * Command to delete a category
 */
public class DeleteCategoryCommand extends BaseCommand<String> {

    public DeleteCategoryCommand(String id) {
        super(id);
    }
}
