import { Injectable } from '@angular/core';
import { ProductDTO } from '../../mesModels/models';

export interface CartItem extends ProductDTO {
  qty: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private items: CartItem[] = [];

  getCart(): CartItem[] {
    return this.items;
  }

  setCart(newCart: CartItem[]) {
    this.items = newCart;
  }

  addToCart(product: ProductDTO, qty: number = 1): void {
    const existing = this.items.find(item => item.id === product.id);
    if (existing) {
      existing.qty += qty;
    } else {
      this.items.push({ ...product, qty });
    }
  }

  removeFromCart(productId: string) {
    this.items = this.items.filter(item => item.id !== productId);
  }

  clearCart() {
    this.items = [];
  }
}