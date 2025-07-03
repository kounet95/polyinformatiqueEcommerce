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
                    // 👉 Ici tu récupères le clientSecret pour Stripe.js
                    console.log('Stripe clientSecret:', clientSecret);

                    // 👉 TODO: Tu peux appeler Stripe.js ici :
                    // this.payWithStripe(clientSecret);

                    this.message = 'Commande créée ! Paiement en attente...';
                    this.cartService.clearCart();
                    this.cartItems = [];
                    this.loading = false;
                    this.step = 1;
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

  // Méthode pour intégrer Stripe.js et confirmer le paiement
async payWithStripe(clientSecret: string) {
  //   on va faire un Chargement de Stripe.js
  const stripeJs = await import('@stripe/stripe-js');
  const stripe = await stripeJs.loadStripe('pk_test_TON_PUBLIC_KEY_STRIPE_ICI');

  if (!stripe) {
    this.message = 'Stripe.js non chargé correctement.';
    return;
  }

  // Creation dun élément Stripe CardElement ou récupère les infos de ta page


  const elements = stripe.elements();
  const cardElement = elements.create('card');
  cardElement.mount('#card-element'); // Assure-toi d'avoir <div id="card-element"></div> dans ton HTML

  // Confirme le paiement
  const { error, paymentIntent } = await stripe.confirmCardPayment(clientSecret, {
    payment_method: {
      card: cardElement,
      billing_details: {
        name: this.customer.firstname + ' ' + this.customer.lastname,
        email: this.customer.email
      }
    }
  });

  if (error) {
    console.error('Stripe Payment Error:', error);
    this.message = 'Erreur de paiement Stripe : ' + error.message;
  } else if (paymentIntent && paymentIntent.status === 'succeeded') {
    this.message = 'Paiement réussi !';
    console.log('PaymentIntent:', paymentIntent);
    // ✅ Tu peux rediriger vers une page de confirmation ici
  } else {
    this.message = 'Paiement non confirmé.';
  }
}


}
