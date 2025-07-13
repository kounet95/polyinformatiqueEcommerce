import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
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
import { loadStripe, Stripe, StripeCardElement } from '@stripe/stripe-js';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { AnnouncementBarComponent } from '../announcement-bar/announcement-bar.component';
import { LikeProductComponent } from '../like-product/like-product.component';

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
    AnnouncementBarComponent,
    LikeProductComponent 
  ],
})
export class CheckoutComponent implements OnInit {

  @ViewChild('cardElement') cardElementRef!: ElementRef;

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

  payment = {
    method: 'card'
  };

  private stripe!: Stripe | null;
  private card!: StripeCardElement;

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private invoiceService: InvoiceService,
    private customerService: CustomerService,
    private authService: AuthService
  ) {}

  async ngOnInit() {
    this.cartItems = this.cartService.getCart();
    this.calculateTotals();
    this.loadCustomer();
    this.stripe = await loadStripe('pk_test_TON_PUBLIC_KEY_ICI');
    this.setupStripeCard();
  }

  setupStripeCard() {
    if (this.stripe && this.cardElementRef) {
      const elements = this.stripe.elements();
      this.card = elements.create('card');
      this.card.mount(this.cardElementRef.nativeElement);
    }
  }

  loadCustomer() {
    const userId = this.authService.getUserId ? this.authService.getUserId() : null;
    if (userId) {
      this.customerService.getCustomerById(userId).subscribe({
        next: customer => this.customer = customer,
        error: () => this.message = "Impossible de charger les informations client."
      });
    }
  }

  calculateTotals() {
    this.subtotal = this.cartItems.reduce(
      (sum, item) => sum + ((item.pricePromo ?? item.productSizePrice) * item.qty),
      0
    );
    this.tax = Math.round(this.subtotal * 0.1 * 100) / 100;
    this.total = this.subtotal + this.shipping + this.tax - this.discount;
  }

  applyPromo() {
    this.discount = this.promoCode === 'PROMO10' ? 10 : 0;
    this.calculateTotals();
  }

  shippingAddressToString(): string {
    return `${this.shippingAddress.street} ${this.shippingAddress.apartment ?? ''}, ${this.shippingAddress.city}, ${this.shippingAddress.state} ${this.shippingAddress.zip}, ${this.shippingAddress.country}`;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
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
      supplierId: '',
      createdAt: new Date().toISOString(),
      orderStatus: OrderStatus.Inprogress,
      paymentMethod: 'card',
      total: this.total,
      barcode: '',
      shippingId: this.shippingAddressToString()
    };

    this.orderService.createOrder(order).subscribe({
      next: orderId => {
        const orderLineRequests = this.cartItems.map(item => {
          const orderLine: OrderLineDTO = {
            id: '',
            orderId,
            stockId: item.productSizeId,
            qty: item.qty
          };
          return this.orderService.addProductToOrder(orderId, orderLine);
        });

        forkJoin(orderLineRequests).subscribe({
          next: () => {
            const invoice: InvoiceDTO = {
              id: '',
              orderId,
              customerId: this.customer.id || this.customer.email || '',
              amount: this.total,
              paymentMethod: 'card',
              restMonthlyPayment: 0,
              paymentStatus: 'WAITING',
              supplierId: ''
            };
            this.invoiceService.createInvoice(invoice).subscribe({
              next: () => {
                this.orderService.createPaymentIntent(order).subscribe({
                  next: (clientSecret) => {
                    this.clientSecret = clientSecret;
                    this.confirmPayment();
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

  async confirmPayment() {
    if (!this.stripe || !this.card || !this.clientSecret) {
      this.message = 'Erreur de configuration Stripe.';
      this.loading = false;
      return;
    }

    this.loading = true;
    const { error, paymentIntent } = await this.stripe.confirmCardPayment(this.clientSecret, {
      payment_method: {
        card: this.card,
        billing_details: {
          name: `${this.customer.firstname} ${this.customer.lastname}`,
          email: this.customer.email
        }
      }
    });

    if (error) {
      this.message = `Erreur de paiement Stripe : ${error.message}`;
      this.loading = false;
    } else if (paymentIntent && paymentIntent.status === 'succeeded') {
      this.message = 'Paiement réussi !';
      this.orderService.confirmOrder(paymentIntent.id).subscribe({
        next: () => {
          this.loading = false;
          this.cartService.clearCart();
        },
        error: () => {
          this.message = 'Erreur lors de la confirmation de la commande.';
          this.loading = false;
        }
      });
    }
  }
}
