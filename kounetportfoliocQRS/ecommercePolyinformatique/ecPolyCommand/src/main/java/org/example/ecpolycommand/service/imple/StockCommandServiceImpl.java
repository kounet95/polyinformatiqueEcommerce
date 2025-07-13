package org.example.ecpolycommand.service.imple;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.ecpolycommand.service.StockCommandService;
import org.example.polyinformatiquecoreapi.commandEcommerce.AddStockCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockCommandServiceImpl implements StockCommandService {

    private final CommandGateway commandGateway;

    public StockCommandServiceImpl(CommandGateway commandGateway) {

      this.commandGateway = commandGateway;
    }

    @Override
    public void addStock(StockDTO dto) {

      commandGateway.sendAndWait(new AddStockCommand(dto.getId(), dto));
    }

    @Service
    public static class StripeService {

      public StripeService(@Value("${stripe.api.secret.key}") String secretKey) {
        Stripe.apiKey = secretKey;
      }

      public PaymentIntent createPaymentIntent(double amount) throws StripeException {
        PaymentIntentCreateParams params =
          PaymentIntentCreateParams.builder()
            .setAmount((long) (amount * 100)) // cents
            .setCurrency("usd")
            .build();

        return PaymentIntent.create(params);
      }
    }
}
