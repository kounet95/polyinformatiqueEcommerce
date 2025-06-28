import { Component, OnInit } from '@angular/core';
import { CartService } from '../services/cartservice';
import { OrderService } from '../services/order.service';
import { InvoiceService } from '../services/invoice.service';
import { CustomerService } from '../services/customer.service'; // Ajouté
import { AuthService } from '../../services/AuthService'; // Ajouté
import { CartItem, CustomerEcommerceDTO, InvoiceDTO, OrderDTO, OrderLineDTO, OrderStatus } from '../../mesModels/models';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
  standalone: false,
})
export class CheckoutComponent implements OnInit {
  step = 1;
  cartItems: CartItem[] = [];
  subtotal = 0;
  shipping = 9.99;
  tax = 0;
  total = 0;
  promoCode = '';
  discount = 0;
  message: string = '';
  loading: boolean = false;

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
    private customerService: CustomerService, // Ajouté
    private authService: AuthService // Ajouté
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
            // Pré-remplir l'adresse de livraison si besoin
            // this.shippingAddress = ... (à adapter selon ton modèle)
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

  placeOrder() {
    this.loading = true;
    this.message = '';

    // Vérification des champs obligatoires
    if (!this.customer.id || !this.customer.email) {
      this.message = "Informations client manquantes.";
      this.loading = false;
      return;
    }

    // Création de la commande
    const order: OrderDTO = {
      id: '',
      customerId: this.customer.id || this.customer.email,
      supplierId: '',
      createdAt: new Date().toISOString(),
      orderStatus: OrderStatus.Inprogress,
      paymentMethod: this.payment.method,
      total: this.total,
      barcode: '',
      shippingId: ''
    };

    this.orderService.createOrder(order).subscribe({
      next: orderId => {
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
              customerId: this.customer.id ? this.customer.id : (this.customer.email ?? ''),
              amount: this.total,
              paymentMethod: this.payment.method,
              restMonthlyPayment: 0,
              paymentStatus: 'WAITING',
              supplierId: ''
            };
            this.invoiceService.createInvoice(invoice).subscribe({
              next: () => {
                this.message = 'Commande et facture créées avec succès !';
                this.cartService.clearCart();
                this.cartItems = [];
                this.loading = false;
                this.step = 1;
              },
              error: () => {
                this.message = 'Erreur lors de la génération de la facture.';
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
}