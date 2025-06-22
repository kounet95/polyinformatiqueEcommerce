import { Component } from '@angular/core';
import { CartService } from '../services/cartservice';
import { OrderService } from '../services/order.service';
import { InvoiceService } from '../services/invoice.service';
import { OrderDTO, OrderLineDTO, InvoiceDTO, OrderStatus,CartItem } from '../../mesModels/models';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-order-create',
  templateUrl: './order-create.component.html',
  standalone:false
})
export class OrderCreateComponent {
  customerId: string = '';
  supplierId: string = '';
  paymentMethod: string = '';
  shippingId: string = '';
  cartItems: CartItem[] = [];
  total: number = 0;
  message: string = '';
  loading: boolean = false;

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
    if (!this.customerId || !this.paymentMethod) {
      this.message = 'Veuillez renseigner le client et le moyen de paiement.';
      return;
    }
    this.loading = true;

    // 1. Création commande
    const order: OrderDTO = {
      id: '',
      customerId: this.customerId,
      supplierId: this.supplierId,
      createdAt: new Date().toISOString(),
      orderStatus: OrderStatus.Inprogress,
      paymentMethod: this.paymentMethod,
      total: this.total,
      barcode: '',
      shippingId: this.shippingId
    };

    this.orderService.createOrder(order).subscribe({
      next: orderId => {
        // 2. Ajout des produits (OrderLineDTO)
        const orderLineRequests = this.cartItems.map(item => {
          const orderLine: OrderLineDTO = {
            id: '',
            orderId: orderId,
            stockId: item.productSizeId,
            qty: item.qty
          };
          return this.orderService.addProductToOrder(orderId, orderLine);
        });

        // 3. Attendre tous les ajouts de produits
        forkJoin(orderLineRequests).subscribe({
          next: () => {
            // 4. Générer la facture
            const invoice: InvoiceDTO = {
              id: '',
              orderId: orderId,
              customerId: this.customerId,
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
        this.message = 'Erreur lors de la commande';
        this.loading = false;
      }
    });
  }
}