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
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { AnnouncementBarComponent } from '../announcement-bar/announcement-bar.component';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
   standalone: true,
  imports: [
    CommonModule,         
    MatIconModule,
    MatSnackBarModule,
    MatTableModule,
    MatExpansionModule,
    RouterModule,
    FormsModule,
    MatListModule,
    AnnouncementBarComponent
  ],
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
    firstName: '',
    lastName: '',
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
    console.log('[Init] Mode:', this.mode);
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
          this.customer = {
            id: customer.id,
            firstName: customer.firstName ?? '',
            lastName: customer.lastName ?? '',
            email: customer.email ?? '',
            phone: customer.phone ?? '',
            addressId: customer.addressId ?? '',
            createdAt: customer.createdAt ?? ''
          };
          console.log('Client chargé :', this.customer);
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

    console.log('[placeOrder] Order:', order);

    this.orderService.createOrder(order).subscribe({
      next: orderId => {
        console.log('[placeOrder] Order ID:', orderId);

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
            const invoice: InvoiceDTO = {
              id: '',
              orderId: orderId,
              customerId: this.customer.id || this.customer.email || '',
              amount: this.total,
              paymentMethod: this.payment.method,
              restMonthlyPayment: 0,
              paymentStatus: 'WAITING',
              supplierId: this.mode === 'supplier' ? this.supplierId : ''
            };

            this.invoiceService.createInvoice(invoice).subscribe({
              next: () => {
                this.orderService.createPaymentIntent(order).subscribe({
                  next: (clientSecret) => {
                    console.log('[placeOrder] Stripe clientSecret:', clientSecret);
                    this.clientSecret = clientSecret;
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

  async payWithStripe(clientSecret: string) {
    const stripeJs = await import('@stripe/stripe-js');
    const stripe = await stripeJs.loadStripe('pk_test_TON_PUBLIC_KEY_STRIPE_ICI');

    if (!stripe) {
      this.message = 'Stripe.js non chargé correctement.';
      return;
    }

    const elements = stripe.elements();
    const cardElement = elements.create('card');
    cardElement.mount('#card-element');

    const { error, paymentIntent } = await stripe.confirmCardPayment(clientSecret, {
      payment_method: {
        card: cardElement,
        billing_details: {
          name: this.customer.firstName + ' ' + this.customer.lastName,
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
    } else {
      this.message = 'Paiement non confirmé.';
    }
  }

}