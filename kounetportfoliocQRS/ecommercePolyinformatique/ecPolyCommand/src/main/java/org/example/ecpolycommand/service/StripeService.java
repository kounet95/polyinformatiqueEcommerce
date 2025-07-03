package org.example.ecpolycommand.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

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

