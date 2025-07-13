package org.example.ecpolycommand.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;

public interface StripeService {
    String createCheckoutSession(InvoiceDTO invoice) throws StripeException;
    Event constructEvent(String payload, String sigHeader) throws Exception;
}
