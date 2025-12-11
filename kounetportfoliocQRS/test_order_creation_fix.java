import org.example.ecpolycommand.aggregate.OrderAggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateOrderCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;
import java.util.ArrayList;
import java.util.List;

public class TestOrderCreationFix {
    public static void main(String[] args) {
        System.out.println("[TEST] Testing OrderAggregate creation fix...");

        try {
            // Create test order DTO with null ID (simulating the issue)
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(null); // This was causing the issue
            orderDTO.setCustomerEmail("test@example.com");
            orderDTO.setSupplierId("supplier123");
            orderDTO.setCurrency("EUR");
            orderDTO.setTotal(100.0);
            orderDTO.setPaymentMethod("CARD");

            List<OrderLineDTO> orderLines = new ArrayList<>();
            OrderLineDTO line = new OrderLineDTO();
            line.setId("line1");
            line.setStockId("stock123");
            line.setQty(2);
            orderLines.add(line);
            orderDTO.setOrderLines(orderLines);

            // Create command with null ID (reproducing the error scenario)
            CreateOrderCommand command = new CreateOrderCommand(null, orderDTO, false);

            // This should now work without throwing IncompatibleAggregateException
            OrderAggregate aggregate = new OrderAggregate(command);

            System.out.println("[TEST] SUCCESS: OrderAggregate created successfully!");
            System.out.println("[TEST] Generated Order ID: " + aggregate.getOrderId());

        } catch (Exception e) {
            System.err.println("[TEST] FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
