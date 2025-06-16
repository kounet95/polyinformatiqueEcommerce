package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteOrderCommand extends BaseCommand<String> {
    public DeleteOrderCommand(String id) {
        super(id);
    }
}
