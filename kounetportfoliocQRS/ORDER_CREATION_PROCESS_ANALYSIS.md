# Analyse du Processus de Création de Commande - E-commerce Polyinformatique

## Vue d'ensemble de l'Architecture

Le système utilise une architecture **CQRS (Command Query Responsibility Segregation)** avec **Event Sourcing** et le pattern **Saga** pour orchestrer les processus métier complexes.

### Composants Principaux

- **Frontend**: Angular avec CheckoutComponent
- **Backend Command**: ecPolyCommand (gestion des commandes)
- **Backend Query**: ecPolyQuery (lecture des données)
- **Pattern Saga**: Orchestration des processus
- **Stripe**: Traitement des paiements
- **Axon Framework**: CQRS et Event Sourcing

## Flux Complet de Création de Commande

### 1. Frontend - CheckoutComponent

**Fichier**: `frontendAngular/src/app/Ecommerce/checkout/checkout.component.ts`

#### Fonctionnalités principales:
- **Gestion du panier**: Affichage des articles, calcul des totaux
- **Informations client**: Collecte des données client et adresse
- **Intégration Stripe**: Gestion des paiements via Stripe Elements
- **Processus en étapes**: Navigation entre les étapes du checkout

#### Méthodes clés:
```typescript
// Finalisation de la commande
finalizeOrder() {
  // Validation des données
  // Création de l'objet OrderDTO
  // Appel au service OrderService
}

// Traitement du paiement
processPayment() {
  // Intégration avec Stripe
  // Confirmation du paiement
}
```

### 2. Backend - OrderController

**Fichier**: `ecPolyCommand/src/main/java/org/example/ecpolycommand/web/OrderController.java`

#### Endpoint principal: `POST /create`

```java
@PostMapping("/create")
public CompletableFuture<String> createOrder(@Valid @RequestBody CreateOrderRequest request)
```

#### Processus de validation:
1. **Génération UUID** pour la commande
2. **Validation des stockIds** pour chaque ligne de commande
3. **Validation de disponibilité** via appel REST au service Query
4. **Envoi de la commande** via CommandGateway

#### Validation des stocks:
```java
// Création des requêtes de validation
List<StockValidationRequest> stockValidationRequests = new ArrayList<>();
for (OrderLineDTO line : orderDTO.getOrderLines()) {
    stockValidationRequests.add(new StockValidationRequest(line.getStockId(), line.getQty()));
}

// Appel au service Query pour validation
String validationUrl = queryServiceUrl + "/api/stocks/validate-availability";
Boolean stockAvailable = restTemplate.postForObject(validationUrl, stockValidationRequests, Boolean.class);
```

### 3. OrderAggregate - Gestion d'État

**Fichier**: `ecPolyCommand/src/main/java/org/example/ecpolycommand/aggregate/OrderAggregate.java`

#### Command Handler - Création:
```java
@CommandHandler
public OrderAggregate(CreateOrderCommand cmd) {
    // Validation des commandes personnalisées
    if (cmd.isCustom() && (cmd.getOrderDTO().getSupplierId() == null || cmd.getOrderDTO().getSupplierId().isBlank())) {
        throw new IllegalArgumentException("Supplier ID must be provided for custom orders");
    }
    
    // Application de l'événement
    apply(new OrderCreatedEvent(cmd.getId(), cmd.getOrderDTO()));
}
```

#### Event Sourcing Handler:
```java
@EventSourcingHandler
public void on(OrderCreatedEvent event) {
    // Initialisation de l'état de l'agrégat
    this.orderId = event.getId();
    this.customerEmail = dto.getCustomerEmail();
    this.orderStatus = OrderStatus.Inprogress;
    this.confirmed = false;
    this.paid = false;
    // ... autres propriétés
}
```

#### Cycle de vie de la commande:
1. **Created** → **Confirmed** → **Invoice Generated** → **Paid** → **Shipped** → **Delivered**

### 4. OrderProcessingSaga - Orchestration

**Fichier**: `ecPolyCommand/src/main/java/org/example/ecpolycommand/saga/OrderProcessingSaga.java`

#### Démarrage de la Saga:
```java
@StartSaga
@SagaEventHandler(associationProperty = "id")
public void on(OrderCreatedEvent event) {
    String orderId = event.getId();
    
    // 1. Confirmation automatique de la commande
    commandGateway.send(new ConfirmOrderCommand(orderId));
    
    // 2. Diminution des stocks pour chaque ligne
    event.getOrderDTO().getOrderLines().forEach(line ->
        commandGateway.send(new DecreaseStockCommand(line.getStockId(), line.getQty()))
    );
    
    // 3. Création du PaymentIntent Stripe
    commandGateway.send(CreatePaymentIntentCommand.builder()
        .orderId(orderId)
        .amountInCents(Math.round(event.getOrderDTO().getTotal() * 100))
        .currency(event.getOrderDTO().getCurrency())
        .build());
    
    // 4. Génération de la facture
    commandGateway.send(new GenerateInvoiceCommand(orderId, invoice));
}
```

#### Gestion des événements de paiement:
```java
@SagaEventHandler(associationProperty = "orderId")
public void on(PaymentCompletedEvent event) {
    commandGateway.send(new CompleteOrderCommand(event.getOrderId()));
    SagaLifecycle.end(); // Fin de la saga
}

@SagaEventHandler(associationProperty = "orderId")
public void on(PaymentFailedEvent event) {
    commandGateway.send(new CancelOrderCommand(event.getOrderId()));
    SagaLifecycle.end(); // Fin de la saga
}
```

### 5. StockAggregate - Gestion des Stocks

**Fichier**: `ecPolyCommand/src/main/java/org/example/ecpolycommand/aggregate/StockAggregate.java`

#### Diminution des stocks:
```java
@CommandHandler
public void handle(DecreaseStockCommand cmd) {
    // Validation de la disponibilité
    if (this.quantity < cmd.getQuantity()) {
        throw new IllegalStateException("Not enough stock for productSizeId=" + this.productSizeId);
    }
    
    // Application de l'événement
    apply(new StockDecreasedEvent(cmd.getStockId(), cmd.getQuantity()));
}

@EventSourcingHandler
public void on(StockDecreasedEvent event) {
    this.quantity -= event.getQuantity();
}
```

### 6. StripeServiceImpl - Traitement des Paiements

**Fichier**: `ecPolyCommand/src/main/java/org/example/ecpolycommand/service/imple/StripeServiceImpl.java`

#### Méthodes principales:
```java
// Création d'une session Checkout
public String createCheckoutSession(InvoiceDTO invoice) throws StripeException

// Création d'un PaymentIntent
public PaymentIntent createPaymentIntent(long amountInCents, String currency) throws StripeException

// Validation des webhooks
public Event constructEvent(String payload, String sigHeader) throws Exception
```

### 7. StripeWebhookController - Gestion des Webhooks

**Fichier**: `ecPolyCommand/src/main/java/org/example/ecpolycommand/web/StripeWebhookController.java`

#### Traitement des événements Stripe:
- Validation de la signature webhook
- Publication d'événements PaymentCompletedEvent ou PaymentFailedEvent
- Intégration avec la Saga pour finaliser le processus

## Diagramme de Flux

```
Frontend (CheckoutComponent)
    ↓ HTTP POST /create
OrderController
    ↓ Validation stocks + CreateOrderCommand
OrderAggregate
    ↓ OrderCreatedEvent
OrderProcessingSaga
    ↓ ConfirmOrderCommand + DecreaseStockCommand + CreatePaymentIntentCommand + GenerateInvoiceCommand
    ├── OrderAggregate (confirmation)
    ├── StockAggregate (diminution stocks)
    ├── StripeService (création PaymentIntent)
    └── OrderAggregate (génération facture)
    ↓
Stripe Webhook
    ↓ PaymentCompletedEvent/PaymentFailedEvent
OrderProcessingSaga
    ↓ CompleteOrderCommand/CancelOrderCommand
OrderAggregate (finalisation)
```

## Avantages de cette Architecture

### 1. **Séparation des Responsabilités**
- Commands pour les modifications
- Queries pour les lectures
- Saga pour l'orchestration

### 2. **Résilience**
- Gestion des échecs de paiement
- Compensation automatique (annulation)
- Event Sourcing pour l'audit

### 3. **Scalabilité**
- Services découplés
- Traitement asynchrone
- Possibilité de scaling indépendant

### 4. **Traçabilité**
- Historique complet des événements
- Logs détaillés à chaque étape
- Possibilité de replay des événements

## Points d'Attention

### 1. **Validation des Stocks**
- Double validation (Controller + Aggregate)
- Gestion des conditions de course
- Compensation en cas d'échec

### 2. **Gestion des Paiements**
- Intégration robuste avec Stripe
- Gestion des webhooks
- Sécurisation des endpoints

### 3. **Cohérence Éventuelle**
- Les données peuvent être temporairement incohérentes
- Nécessité de gérer les états intermédiaires
- Importance des timeouts et retry

## Recommandations d'Amélioration

### 1. **Monitoring et Observabilité**
- Ajout de métriques détaillées
- Dashboards pour suivre les commandes
- Alertes en cas d'échec

### 2. **Tests**
- Tests d'intégration pour le flux complet
- Tests de charge pour la validation des stocks
- Tests de résilience pour les échecs de paiement

### 3. **Sécurité**
- Validation renforcée des inputs
- Chiffrement des données sensibles
- Audit des accès

Cette architecture offre une base solide pour un système e-commerce robuste et scalable, avec une séparation claire des responsabilités et une gestion efficace des processus métier complexes.
