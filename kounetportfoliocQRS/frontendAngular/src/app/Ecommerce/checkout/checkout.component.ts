import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CartService } from '../services/cartservice';
import { OrderService } from '../services/order.service';
import { InvoiceService } from '../services/invoice.service';
import { CustomerService } from '../services/customer.service';
import { AuthService } from '../../services/AuthService';
import {
  AddressDTO,
  CartItem,
  CustomerEcommerceDTO,
  InvoiceDTO,
  OrderDTO,
  OrderLineDTO,
  OrderStatus
} from '../../mesModels/models';
import { forkJoin, firstValueFrom } from 'rxjs';
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
  customDescription: string = '';

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

  // Gestion des adresses
  addresses: AddressDTO[] = [];
  selectedAddressId?: string = '';

  shippingAddress: Partial<AddressDTO> = {
    street: '',
    appartment: '',
    city: '',
    state: '',
    zip: '',
    country: ''
  };

  payment = {
    method: 'card',
    name: ''
  };

  orderMode: 'CART' | 'CUSTOM' = 'CART';
  customSupplierId: string = '';

  currency: string = 'eur';

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
    this.stripe = await loadStripe('pk_test_51RjaG74EMj4mRh4Ig9G6XBkhmBu7e3fsqGmKkrZZ3WVQA3t9AvkP4zZuy4FQJBS6yfxzH7pi03K9N4beuis76nrn004vakKS5x');
    this.setupStripeCard();
    this.loadAddresses();
  }

  setupStripeCard() {
    if (this.stripe && this.cardElementRef) {
      const elements = this.stripe.elements();
      this.card = elements.create('card');
      this.card.mount(this.cardElementRef.nativeElement);
    }
  }

  loadCustomer() {
    const profile = this.authService.getUserProfile();

    if (profile?.email) {
      this.customerService.getCustomerByEmail(profile.email).subscribe({
        next: customer => {
          this.customer = {
            ...customer,
            firstname: customer.firstname || profile.firstname,
            lastname: customer.lastname || profile.lastname,
            email: customer.email || profile.email,
            phone: customer.phone || profile.phone
          };
        },
        error: () => {
          this.customer = {
            id: '',
            firstname: profile.firstname,
            lastname: profile.lastname,
            email: profile.email,
            phone: profile.phone,
            addressId: '',
            createdAt: ''
          };
          this.message = "Profil chargé depuis Keycloak uniquement.";
        }
      });
    } else {
      this.message = "Impossible de trouver l'email utilisateur.";
    }
  }

  loadAddresses() {
    const customerId = this.authService.getUserId();
    if (customerId) {
      this.customerService.getAddressesByCustomerId(customerId).subscribe({
        next: (addresses) => {
          this.addresses = addresses;
          if (addresses.length > 0) {
            this.selectedAddressId = addresses[0].id;
            this.setShippingAddress(addresses[0]);
          }
        },
        error: () => console.error("Impossible de charger les adresses.")
      });
    }
  }
onAddressChange() {
  const address = this.addresses.find(a => a.id === this.selectedAddressId);
  if (address) {
    this.setShippingAddress(address);
  } else {
    // cas où aucune adresse n’est trouvée (optionnel)
    console.warn('Adresse non trouvée pour id:', this.selectedAddressId);
  }
}

  setShippingAddress(address: AddressDTO) {
    this.shippingAddress = { ...address };
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
    return `${this.shippingAddress.street} ${this.shippingAddress.appartment ?? ''}, ${this.shippingAddress.city}, ${this.shippingAddress.state} ${this.shippingAddress.zip}, ${this.shippingAddress.country}`;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  async payer() {
    this.loading = true;
    this.message = '';

    if (!this.customer.email) {
      this.message = "Informations client manquantes.";
      this.loading = false;
      return;
    }

    const order: OrderDTO = {
      id: '',
      customerEmail: this.customer.email,
      supplierId: this.orderMode === 'CUSTOM' ? this.customSupplierId.trim() : '',
      createdAt: new Date().toISOString(),
      orderStatus: OrderStatus.Inprogress,
      paymentMethod: this.payment.method,
      total: this.total,
      barcode: '',
      currency: this.currency,
      shippingId: this.shippingAddressToString(),
      description: this.orderMode === 'CUSTOM' ? this.customDescription : undefined,
    };

    try {
      const orderId = await firstValueFrom(
        this.orderService.createOrder(order, this.orderMode === 'CUSTOM')
      );

      const invoice: InvoiceDTO = {
        id: '',
        orderId,
        customerEmail: this.customer.id || '',
        amount: this.total,
        paymentMethod: this.payment.method,
        restMonthlyPayment: 0,
        paymentStatus: 'WAITING',
        supplierId: this.orderMode === 'CUSTOM' ? this.customSupplierId.trim() : ''
      };

      await firstValueFrom(this.invoiceService.createInvoice(invoice));
      const paymentIntentResponse = await firstValueFrom(this.orderService.createPaymentIntent(order));
      this.clientSecret = paymentIntentResponse.client_secret;
      await this.confirmPayment();

    } catch (err) {
      this.message = "Erreur lors du traitement de la commande.";
      this.loading = false;
    }
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
