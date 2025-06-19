import { Component } from '@angular/core';
import { CartService } from '../services/cartservice';
import { OrderService } from '../services/order.service';
import { ProductDTO, OrderDTO, OrderLineDTO, OrderStatus } from '../../mesModels/models';

@Component({
  selector: 'app-order-create',
  templateUrl: './order-create.component.html',
  standalone: false,
})
export class OrderCreateComponent {
  customerId = '';
  supplierId = '';
  paymentMethod = '';
  shippingId = '';
  cartItems: ProductDTO[] = [];
  total = 0;
  message = '';

  constructor(private cartService: CartService, private orderService: OrderService) {
    this.cartItems = this.cartService.getCart();
    // On additionne tous les prix des tailles principales (premier ProductSize) de chaque produit du panier
    this.total = this.cartItems.reduce((sum, item) => sum + (item.productSizes[0]?.price ?? 0), 0);
  }

  onSubmit() {
    if (!this.customerId || !this.paymentMethod) {
      this.message = 'Veuillez renseigner le client et le moyen de paiement.';
      return;
    }
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
        // Créer les lignes de commande pour chaque produit du panier
        this.cartItems.forEach(item => {
          const orderLine: OrderLineDTO = {
            id: '',
            orderId: orderId,
            stockId: item.productSizes[0]?.id ?? '', // id de la première taille
            qty: 1
          };
          this.orderService.addProductToOrder(orderId, orderLine).subscribe();
        });
        this.message = 'Commande créée avec succès !';
        this.cartService.clearCart();
      },
      error: () => this.message = 'Erreur lors de la commande'
    });
  }
}