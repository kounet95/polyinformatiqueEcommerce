import { Component } from '@angular/core';
import { CartService } from '../services/cartservice';
import { OrderService } from '../services/order.service';
import { InvoiceService } from '../services/invoice.service';
import { OrderDTO, OrderLineDTO, InvoiceDTO, OrderStatus, CartItem } from '../../mesModels/models';
import { forkJoin } from 'rxjs';

export const cartFixti: CartItem[] = [
  {
    productId: '4b624571-6347-4e94-a606-7fc91fd7f5b2',
    productName: 'T-shirt coton bio',
    productImg: 'https://picsum.photos/200/200?random=1',
    qty: 2,
    productSizeId: 'size-s',
    productSize: 'S',
    productSizePrice: 15,
    pricePromo: 12,
    stockIds: ['stock-101', 'stock-104', 'stock-105', 'stock-106']
  },
  {
    productId: 'e283b90e-b530-4507-a221-f87f2579eab2',
    productName: 'Pantalon slim',
    productImg: 'https://picsum.photos/200/200?random=2',
    qty: 1,
    productSizeId: 'size-m',
    productSize: 'M',
    productSizePrice: 40,
    pricePromo: 35,
    stockIds: ['stock-101', 'stock-104', 'stock-105', 'stock-106']
  },
  {
    productId: '5887f8cc-7319-4ea7-a000-e5b5e8e43ddc',
    productName: 'Chaussures running',
    productImg: 'https://picsum.photos/200/200?random=3',
    qty: 1,
    productSizeId: 'size-42',
    productSize: '42',
    productSizePrice: 60,
    pricePromo: 50,
    stockIds: ['stock-101', 'stock-104', 'stock-105', 'stock-106']
  }
];

@Component({
  selector: 'app-order-create',
  templateUrl: './order-create.component.html',
  standalone: false
})
export class OrderCreateComponent {
  customerEmail = '';
  supplierId = '';
  paymentMethod = '';
  shippingId = '';
  cartItems: CartItem[] = [];
  total = 0;
  message = '';
  loading = false;

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private invoiceService: InvoiceService
  ) {
    this.cartItems = this.cartService.getCart();
    this.total = this.cartItems.reduce(
      (sum, item) => sum + (item.pricePromo ?? item.productSizePrice) * item.qty,
      0
    );
  }

  onSubmit() {
    if (!this.customerEmail || !this.paymentMethod) {
      this.message = 'Veuillez renseigner le client et le moyen de paiement.';
      return;
    }
    this.loading = true;

    // 1. Création de la commande (sans orderLines d’abord)
    const order: OrderDTO = {
      customerEmail: this.customerEmail,
      supplierId: this.supplierId,
      createdAt: new Date().toISOString(),
      orderStatus: OrderStatus.Inprogress,
      paymentMethod: this.paymentMethod,
      total: this.total,
      barcode: '',
      shippingId: this.shippingId,
      orderLines: [] // on ajoutera après via l’API
    };

    this.orderService.createOrder(order, false).subscribe({
      next: (orderId: string) => {
        // 2. Ajout des lignes de commande
        const orderLineRequests = this.cartItems.flatMap(item =>
          (item.stockIds ?? []).map(stockId => {
            const orderLine: OrderLineDTO = {
              id: '',
              orderId,
              stockId: stockId, // ✅ single string
              qty: item.qty
            };
            return this.orderService.addProductToOrder(orderId, orderLine);
          })
        );

        forkJoin(orderLineRequests).subscribe({
          next: () => {
            // 3. Création de la facture (juste avec l’ID de la commande)
            const invoice: InvoiceDTO = {
              id: '',
              orderId: orderId as any, // ⚠️ à corriger si tu changes le modèle pour string
              customerEmail: this.customerEmail,
              amount: this.total,
              paymentMethod: this.paymentMethod,
              restMonthlyPayment: 0,
              paymentStatus: 'WAITING',
              supplierId: this.supplierId
            };

            this.invoiceService.createInvoice(invoice).subscribe({
              next: () => {
                this.message = 'Commande et facture créées avec succès !';
                this.cartService.setCart([]);
                this.loading = false;
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
        this.message = 'Erreur lors de la commande.';
        this.loading = false;
      }
    });
  }
}
