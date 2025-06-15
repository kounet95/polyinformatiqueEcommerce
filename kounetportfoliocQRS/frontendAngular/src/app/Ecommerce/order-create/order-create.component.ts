import { Component } from '@angular/core';
import { CartService } from '../services/cartservice';
import { OrderService } from '../services/order.service';
import { ProductDTO, OrderDTO, OrderLineDTO } from '../../mesModels/models';

@Component({
  selector: 'app-order-create',
  templateUrl: './order-create.component.html',
  standalone:false,
})
export class OrderCreateComponent {
  customerId = '';
  paymentMethod = '';
  cartItems: ProductDTO[];
  total = 0;
  message = '';

  constructor(private cartService: CartService, private orderService: OrderService) {
    this.cartItems = this.cartService.getCart();
    this.total = this.cartItems.reduce((sum, item) => sum + item.price, 0);
  }

  onSubmit() {
    const order: OrderDTO = {
      id: '',
      customerId: this.customerId,
      createdAt: new Date().toISOString(),
      orderStatus: 'PENDING',
      paymentMethod: this.paymentMethod,
      total: this.total,
      barcode: ''
    };
    this.orderService.createOrder(order).subscribe({
      next: orderId => {
        // Créer les lignes de commande pour chaque produit
        this.cartItems.forEach(item => {
          const orderLine: OrderLineDTO = {
            id: '',
            orderId: orderId,
            productSizeId: item.productSize, // ou autre champ selon ton modèle
            qty: 1 // à adapter si gestion des quantités
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