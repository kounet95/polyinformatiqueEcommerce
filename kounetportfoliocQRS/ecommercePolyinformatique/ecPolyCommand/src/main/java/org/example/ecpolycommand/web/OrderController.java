package org.example.ecpolycommand.web;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.ecpolycommand.config.StripeConfigProperties;
import org.example.ecpolycommand.service.imple.StripeServiceImpl;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CreateOrderRequest;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/order/command")
public class OrderController {
  //  Renomme la classe : un nom explicite ! (Order -> OrderController)

  private final CommandGateway commandGateway;
  private final EventStore eventStore;
  private final StripeServiceImpl stripeService;
  private final StripeConfigProperties config;
  private final RestTemplate restTemplate;

  @Value("${ecpolyquery.service.url:http://localhost:8082}")
  private String queryServiceUrl;

  public OrderController(CommandGateway commandGateway,
                         EventStore eventStore,
                         StripeServiceImpl stripeService,
                         StripeConfigProperties config,
                         RestTemplate restTemplate) {
    this.commandGateway = commandGateway;
    this.eventStore = eventStore;
    this.stripeService = stripeService;
    this.config = config;
    this.restTemplate = restTemplate;
  }

  /**
   * Crée une nouvelle commande et envoie ensuite la confirmation
   */
  @PostMapping("/create")
  public CompletableFuture<String> createOrder(@Valid @RequestBody CreateOrderRequest request) {
    OrderDTO orderDTO = request.getOrderDTO();
    orderDTO.setId(UUID.randomUUID().toString());

    // Validation des stockIds
    for (OrderLineDTO line : orderDTO.getOrderLines()) {
      if (line.getStockId() == null || line.getStockId().isEmpty()) {
        throw new IllegalArgumentException("Chaque ligne de commande doit avoir un stockId non nul");
      }
    }

    // Validation de la disponibilité des stocks
    List<StockValidationRequest> stockValidationRequests = new ArrayList<>();
    for (OrderLineDTO line : orderDTO.getOrderLines()) {
      stockValidationRequests.add(new StockValidationRequest(line.getStockId(), line.getQty()));
    }

    try {
      String validationUrl = queryServiceUrl + "/api/stocks/validate-availability";
      Boolean stockAvailable = restTemplate.postForObject(validationUrl, stockValidationRequests, Boolean.class);

      if (stockAvailable == null || !stockAvailable) {
        throw new IllegalArgumentException("Stock insuffisant pour une ou plusieurs lignes de commande");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Erreur lors de la validation du stock: " + e.getMessage());
    }

    return commandGateway.send(new CreateOrderCommand(orderDTO.getId(), orderDTO, request.isCustom()));
  }

  // Classe interne pour la validation des stocks
  public static class StockValidationRequest {
    private String stockId;
    private int requestedQuantity;

    public StockValidationRequest() {}

    public StockValidationRequest(String stockId, int requestedQuantity) {
      this.stockId = stockId;
      this.requestedQuantity = requestedQuantity;
    }

    public String getStockId() { return stockId; }
    public void setStockId(String stockId) { this.stockId = stockId; }
    public int getRequestedQuantity() { return requestedQuantity; }
    public void setRequestedQuantity(int requestedQuantity) { this.requestedQuantity = requestedQuantity; }
  }

  /**
   *  Ajoute un produit à la commande
   */
  @PostMapping("/{orderId}/add-product")
  public CompletableFuture<String> addProductToOrder(@PathVariable String orderId, @Valid @RequestBody OrderLineDTO orderLine) {
    String orderLineId = UUID.randomUUID().toString();

    OrderLineDTO orderLineDTO = new OrderLineDTO(
      orderLineId,
      orderId,
      orderLine.getStockId(),
      orderLine.getQty()
    );

    AddProductToOrderCommand command = new AddProductToOrderCommand(orderId, orderLineDTO);
    return commandGateway.send(command);
  }

  /**
   *  Confirme la commande
   */
  @PutMapping("/{orderId}/confirm")
  public CompletableFuture<String> confirmOrder(@PathVariable String orderId) {
    return commandGateway.send(new ConfirmOrderCommand(orderId));
  }

  /**
   *  Génère la facture pour la commande
   */
  @PostMapping("/{orderId}/generate-invoice")
  public CompletableFuture<String> generateInvoice(@PathVariable String orderId, @Valid @RequestBody InvoiceDTO invoice) {
    return commandGateway.send(new GenerateInvoiceCommand(orderId, invoice));
  }

  /**
   *  Déclenche le paiement de la facture
   * Ici attention ! Normalement, c'est l’orderId, pas l’invoiceId !
   */
  @PutMapping("/{orderId}/pay-invoice")
  public CompletableFuture<String> payInvoice(
    @PathVariable String orderId,
    @RequestBody InvoiceDTO invoice,
    @RequestParam String paymentIntentId,
    @RequestParam String sessionId
  ) {
    return commandGateway.send(new PayInvoiceCommand(orderId, invoice, paymentIntentId, sessionId));
  }


  /**
   *  Démarre l’expédition
   */
  @PutMapping("/{orderId}/start-shipping")
  public CompletableFuture<String> startShipping(@PathVariable String orderId) {
    return commandGateway.send(new StartShippingCommand(orderId));
  }

  /**
   *  Marque la commande comme livrée
   */
  @PutMapping("/{orderId}/deliver")
  public CompletableFuture<String> deliverOrder(@PathVariable String orderId) {
    return commandGateway.send(new DeliverOrderCommand(orderId));
  }

  /**
   *  Crée un PaymentIntent Stripe
   */
  @PostMapping("/payment-intent")
  public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody OrderDTO order) {
    try {
      long amountInCents = Math.round(order.getTotal() * 100);

      if (amountInCents < 50) {
        return ResponseEntity.badRequest()
          .contentType(MediaType.APPLICATION_JSON)
          .body(Map.of("error", "Montant trop petit : minimum 50 centimes requis."));
      }

      String currency = order.getCurrency();

      PaymentIntent paymentIntent = stripeService.createPaymentIntent(amountInCents, currency);

      Map<String, String> response = Map.of("client_secret", paymentIntent.getClientSecret());
      return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(response);
    } catch (StripeException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Map.of("error", "Erreur Stripe : " + e.getMessage()));
    }
  }

//  /**
//   *  Crée un cheick Stripe
//   */
//  @PostMapping("/cheick")
//
//  public String cheickingPaymentIntent(Model model,
//                                       @RequestBody OrderDTO order ) {
//
//    Stripe.apiKey = config.getApi().getKey();
//      model.addAttribute("order", order);
//      model.addAttribute("stripeKeyPublic", order);
//      model.addAttribute("order", order.getCustomerId());
//      model.addAttribute("currency", "USD");
//      model.addAttribute("order", order.getCustomerId());
//
//
//  }
  /**
   *  Annule la commande
   */
  @DeleteMapping("/{orderId}")
  public CompletableFuture<String> cancelOrder(@PathVariable String orderId,
                                               @RequestParam(defaultValue = "Cancelled by user") String reason) {
    return commandGateway.send(new CancelOrderCommand(orderId));
  }

  /**
   * Voir l’historique des événements pour debug / audit
   */
  @GetMapping("/events/{aggregateId}")
  public Stream<?> eventsStream(@PathVariable String aggregateId) {
    return eventStore.readEvents(aggregateId).asStream();
  }

  /**
   * Gestion d’erreur générique
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> exceptionHandler(Exception exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(" Erreur : " + exception.getMessage());
  }
}
