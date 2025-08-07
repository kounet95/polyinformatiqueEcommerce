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
  OrderStatus
} from '../../mesModels/models';
import { firstValueFrom } from 'rxjs';
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
  private card?: StripeCardElement;
  private lastOrderId = '';

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
    this.loadAddresses();
    // NE PAS monter la carte ici, on le fait à l'étape 3
  }

  setupStripeCard() {
    if (this.stripe && this.cardElementRef) {
      if (!this.card) {
        const elements = this.stripe.elements();
        this.card = elements.create('card');
        this.card.mount(this.cardElementRef.nativeElement);
        // console.log('Stripe card mounted !');
      }
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
    if (this.step === 3) {
      setTimeout(() => this.setupStripeCard(), 0);
    }
  }

  prevStep() {
    this.step--;
  }

  /** Paiement Stripe déclenché à l’étape 4 (Review & Place Order) */
async payer() {
  this.loading = true;
  this.message = '';

  // Validation des informations client
  if (!this.customer?.email || !this.customer?.firstname || !this.customer?.lastname) {
    this.message = "Informations client incomplètes.";
    this.loading = false;
    return;
  }

  // Création de la commande
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
    // Étape 1 : créer la commande
    const orderId = await firstValueFrom(
      this.orderService.createOrder(order, this.orderMode === 'CUSTOM')
    );
    this.lastOrderId = orderId;

    // Étape 2 : créer la facture
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

    // Étape 3 : créer le PaymentIntent Stripe
    const paymentIntentResponse = await firstValueFrom(
      this.orderService.createPaymentIntent(order)
    );

    if (!paymentIntentResponse?.client_secret) {
      this.message = "Client secret non reçu de Stripe.";
      this.loading = false;
      return;
    }

    this.clientSecret = paymentIntentResponse.client_secret;

    // Étape 4 : confirmer le paiement avec Stripe
    if (!this.stripe || !this.card) {
      this.message = "Stripe ou carte non initialisée.";
      this.loading = false;
      return;
    }

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
      console.error("Erreur Stripe:", error);
      this.message = `Erreur de paiement Stripe : ${error.message}`;
      this.loading = false;
      return;
    }

    if (paymentIntent && paymentIntent.status === 'succeeded') {
      this.message = 'Paiement réussi !';
      this.step = 5; // Étape confirmation
    } else {
      this.message = "Paiement non confirmé.";
    }

  } catch (err: any) {
    console.error("Erreur dans payer():", err);
    this.message = "Erreur lors du traitement de la commande.";
  } finally {
    this.loading = false;
  }
}


  /** Méthode confirmOrder appelée à l’étape 5 */
  confirmOrder() {
    if (!this.lastOrderId) {
      this.message = "Erreur : commande introuvable.";
      return;
    }
    this.loading = true;
    this.orderService.confirmOrder(this.lastOrderId).subscribe({
      next: () => {
        this.cartService.clearCart();
        this.message = 'Commande confirmée !';
        this.loading = false;
      },
      error: () => {
        this.message = 'Erreur lors de la confirmation de la commande.';
        this.loading = false;
      }
    });
  }
}
