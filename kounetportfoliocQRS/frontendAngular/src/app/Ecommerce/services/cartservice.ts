import { Injectable } from '@angular/core';
import { ProductSizeDTO, CartItem } from '../../mesModels/models';

const CART_KEY = 'cart_items';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private items: CartItem[] = [];

  constructor() {
    this.loadCart();
  }

  private loadCart() {
    const stored = localStorage.getItem(CART_KEY);
    this.items = stored ? JSON.parse(stored) : [];
  }

  private saveCart() {
    localStorage.setItem(CART_KEY, JSON.stringify(this.items));
  }

  getCart(): CartItem[] {
    this.loadCart();
    return this.items;
  }

  setCart(newCart: CartItem[]) {
    this.items = newCart;
    this.saveCart();
  }

  addToCart(productSize: ProductSizeDTO, qty: number = 1): void {
    this.loadCart();

    if (!productSize.product) {
      console.error('ProductDTO manquant sur ProductSizeDTO !');
      return;
    }

    const existing = this.items.find(
      item =>
        item.productSizeId === productSize.id &&
        item.productId === productSize.product!.id
    );

    if (existing) {
      existing.qty += qty;
    } else {
      this.items.push({
        productId: productSize.product.id,
        productName: productSize.product.name,
        productImg: productSize.frontUrl || '',
        qty,
        productSizeId: productSize.id,
        productSize: productSize.sizeProd,
        productSizePrice: productSize.price,
        pricePromo: productSize.pricePromo
      });
    }

    this.saveCart();
  }

  removeFromCart(productSizeId: string) {
    this.loadCart();
    this.items = this.items.filter(item => item.productSizeId !== productSizeId);
    this.saveCart();
  }

  clearCart() {
    this.items = [];
    this.saveCart();
  }
}
