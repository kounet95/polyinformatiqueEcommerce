import { Component, OnInit } from '@angular/core';
import { CartService } from '../services/cartservice';
import { OrderService } from '../services/order.service';
import { InvoiceService } from '../services/invoice.service';
import { CartItem, CustomerEcommerceDTO, InvoiceDTO, OrderDTO, OrderLineDTO, OrderStatus } from '../../mesModels/models';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
  standalone:false,
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

  // Information
  customer: CustomerEcommerceDTO = {
    id: '',
    firstname: '',
    lastname: '',
    email: '',
    phone: '',
    addressId: '',
    createdAt: ''
  };

  // Shipping
  shippingAddress: {
    street: string;
    apartment: string;
    city: string;
    state: string;
    zip: string;
    country: string;
  } = {
    street: '',
    apartment: '',
    city: '',
    state: '',
    zip: '',
    country: ''
  };

  // Payment
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
    private invoiceService: InvoiceService
  ) {}

  ngOnInit() {
    this.cartItems = this.cartService.getCart();
    this.calculateTotals();
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
    // 1. Creation dune commande
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
        // 2. Ajout des products a la commande
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
            // 3. Générer la facture
            const invoice: InvoiceDTO = {
              id: '',
              orderId: orderId,
              customerId: this.customer.id || this.customer.email,
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
              error: (err) => {
                this.message = 'Erreur lors de la génération de la facture.';
                this.loading = false;
              }
            });
          },
          error: (err) => {
            this.message = "Erreur lors de l'ajout des produits à la commande.";
            this.loading = false;
          }
        });
      },
      error: (err) => {
        this.message = "Erreur lors de la création de la commande.";
        this.loading = false;
      }
    });
  }
}