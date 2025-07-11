import { Component, OnInit } from '@angular/core';
import { CartService } from '../services/cartservice';
import { OrderService } from '../services/order.service';
import { InvoiceService } from '../services/invoice.service';
import { CustomerService } from '../services/customer.service';
import { AuthService } from '../../services/AuthService';
import {
  CartItem,
  CustomerEcommerceDTO,
  InvoiceDTO,
  OrderDTO,
  OrderLineDTO,
  OrderStatus
} from '../../mesModels/models';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
  standalone: false,
})
export class CheckoutComponent implements OnInit {

  step = 1;
  clientSecret: string = '';
  cartItems: CartItem[] = [];
  subtotal = 0;
  shipping = 9.99;
  tax = 0;
  total = 0;
  promoCode = '';
  discount = 0;
  message = '';
  loading = false;

  mode: 'customer' | 'supplier' = 'customer';
  supplierId = 'SUPPLIER_XYZ';

  customer: CustomerEcommerceDTO = {
    id: '',
    firstname: '',
    lastname: '',
    email: '',
    phone: '',
    addressId: '',
    createdAt: ''
  };

  shippingAddress = {
    street: '',
    apartment: '',
    city: '',
    state: '',
    zip: '',
    country: ''
  };

  payment: any = {
    method: 'card',
    cardNumber: '',
    exp: '',
    cvv: '',
    name: ''
  };

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private invoiceService: InvoiceService,
    private customerService: CustomerService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.cartItems = this.cartService.getCart();
    this.calculateTotals();
    this.loadCustomer();
  }

  loadCustomer() {
    const userId = this.authService.getUserId ? this.authService.getUserId() : null;
    if (userId) {
      this.customerService.getCustomerById(userId).subscribe({
        next: (customer) => {
          if (customer) {
            this.customer = customer;
          }
        },
        error: () => {
          this.message = "Impossible de charger les informations client.";
        }
      });
    }
  }

  calculateTotals() {
    this.subtotal = this.cartItems.reduce((sum, item) =>
      sum + ((item.pricePromo ?? item.productSizePrice) * item.qty), 0
    );
    this.tax = Math.round(this.subtotal * 0.1 * 100) / 100;
    this.total = this.subtotal + this.shipping + this.tax - this.discount;
  }

  applyPromo() {
    if (this.promoCode === 'PROMO10') {
      this.discount = 10;
    } else {
      this.discount = 0;
    }
    this.calculateTotals();
  }

  nextStep() { this.step++; }
  prevStep() { this.step--; }

  shippingAddressToString(): string {
    return `${this.shippingAddress.street} ${this.shippingAddress.apartment ?? ''} ${this.shippingAddress.city},
     ${this.shippingAddress.state} ${this.shippingAddress.zip} ${this.shippingAddress.country}`;
  }

  placeOrder() {
    this.loading = true;
    this.message = '';

    if (!this.customer.id && !this.customer.email) {
      this.message = "Informations client manquantes.";
      this.loading = false;
      return;
    }

    const order: OrderDTO = {
      id: '',
      customerId: this.customer.id || this.customer.email,
      supplierId: this.mode === 'supplier' ? this.supplierId : '',
      createdAt: new Date().toISOString(),
      orderStatus: OrderStatus.Inprogress,
      paymentMethod: this.payment.method,
      total: this.total,
      barcode: '',
      shippingId: this.shippingAddressToString()
    };

    // 1️⃣ Créer la commande
    this.orderService.createOrder(order).subscribe({
      next: orderId => {

        // 2️⃣ Ajouter les lignes produits
        const orderLineRequests = this.cartItems.map(item => {
          const orderLine: OrderLineDTO = {
            id: '',
            orderId: orderId,
            stockId: item.productSizeId,
            qty: item.qty
          };
          return this.orderService.addProductToOrder(orderId, orderLine);
        });

        forkJoin(orderLineRequests).subscribe({
          next: () => {

            // 3️⃣ Générer la facture
            const invoice: InvoiceDTO = {
              id: '',
              orderId: orderId,
              customerId: this.customer.id ? this.customer.id : (this.customer.email ?? ''),
              amount: this.total,
              paymentMethod: this.payment.method,
              restMonthlyPayment: 0,
              paymentStatus: 'WAITING',
              supplierId: this.mode === 'supplier' ? this.supplierId : ''
            };

            this.invoiceService.createInvoice(invoice).subscribe({
              next: () => {

                // 4️⃣ Créer le PaymentIntent côté backend pour Stripe
                this.orderService.createPaymentIntent(order).subscribe({
                  next: (clientSecret) => {
                    console.log('Stripe clientSecret:', clientSecret);
                    this.clientSecret = clientSecret;

                    // 👉 Ici on appelle le paiement Stripe :
                    this.payWithStripe(this.clientSecret);

                  },
                  error: () => {
                    this.message = 'Erreur lors de la création du PaymentIntent Stripe.';
                    this.loading = false;
                  }
                });

              },
              error: () => {
                this.message = 'Erreur lors de la création de la facture.';
                this.loading = false;
              }
            });

          },
          error: () => {
            this.message = "Erreur lors de l'ajout des produits à la commande.";
            this.loading = false;
          }
        });

      },
      error: () => {
        this.message = "Erreur lors de la création de la commande.";
        this.loading = false;
      }
    });
  }

  async payWithStripe() {
  try {
    // 1️⃣ Charge Stripe.js dynamiquement
    const stripeJs = await import('@stripe/stripe-js');
    const stripe = await stripeJs.loadStripe('pk_test_TON_PUBLIC_KEY_ICI');

    if (!stripe) {
      this.message = 'Stripe n\'a pas pu être chargé.';
      return;
    }

    // 2️⃣ Crée les Elements
    const elements = stripe.elements();
    const cardElement = elements.create('card');
    cardElement.mount('#card-element');

    // 👉 ⚠️ Normalement le mount est fait 1 seule fois dans ngAfterViewInit
    // Ici c'est pour exemple rapide

    // 3️⃣ Confirme le paiement
    const { error, paymentIntent } = await stripe.confirmCardPayment(this.clientSecret, {
      payment_method: {
        card: cardElement,
        billing_details: {
          name: `${this.customer.firstname} ${this.customer.lastname}`,
          email: this.customer.email
        }
      }
    });

    if (error) {
      console.error(error);
      this.message = `Erreur de paiement Stripe : ${error.message}`;
    } else if (paymentIntent && paymentIntent.status === 'succeeded') {
      this.message = 'Paiement réussi avec Stripe !';
      // ✅ Finalise la commande côté backend
      this.placeOrder();
    }
  } catch (err) {
    console.error(err);
    this.message = 'Erreur inattendue Stripe.';
  }
}


 launchApplePay() {
  // ⚡️ Exemple de base pour montrer le flow
  console.log('Apple Pay...');

  if (window.ApplePaySession && ApplePaySession.canMakePayments()) {
    const request = {
      countryCode: 'US',
      currencyCode: 'USD',
      total: {
        label: 'Ma Boutique',
        amount: this.total.toFixed(2)
      },
      supportedNetworks: ['visa', 'masterCard', 'amex'],
      merchantCapabilities: ['supports3DS']
    };

    const session = new ApplePaySession(3, request);

    session.onvalidatemerchant = async (event) => {
      console.log('Validation Apple Pay...');
      // 👉 Tu dois appeler ton backend pour valider le marchand
      // Exemple fictif :
      const merchantSession = {}; // Réponse de ton backend
      session.completeMerchantValidation(merchantSession);
    };

    session.onpaymentauthorized = (event) => {
      console.log('Paiement autorisé Apple Pay...');
      session.completePayment(ApplePaySession.STATUS_SUCCESS);
      this.message = 'Paiement Apple Pay OK';
      this.placeOrder();
    };

    session.begin();
  } else {
    this.message = 'Apple Pay non disponible.';
  }
}



}
