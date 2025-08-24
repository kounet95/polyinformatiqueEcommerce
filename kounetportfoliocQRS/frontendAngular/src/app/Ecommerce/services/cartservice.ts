import { Injectable, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ProductSizeDTO, CartItem, StockDTO } from '../../mesModels/models';
import { StockService } from './stock.service';

const CART_KEY = 'cart_items';

@Injectable({
  providedIn: 'root'
})
export class CartService implements OnInit {
  private items: CartItem[] = [];


  //Observables
  private cartCountSubject = new BehaviorSubject<number>(0);
  cartCount$ = this.cartCountSubject.asObservable();

  private cartItemsSubject = new BehaviorSubject<CartItem[]>([]);
  cartItems$ = this.cartItemsSubject.asObservable();

  constructor(private stockService: StockService) {
    this.loadCart();
    this.updateCartCount();
    this.updateCartItems(); // init liste
  }

  ngOnInit(): void {}
  /** pour mettre à jour le compteur */
  private updateCartCount(): void {
    const count = this.items.reduce((sum, item) => sum + item.qty, 0);
    this.cartCountSubject.next(count);
  }

  /** pour mettre à jour les articles */
  private updateCartItems(): void {
    this.cartItemsSubject.next(this.items);
  }

  private loadCart(): void {
    const stored = localStorage.getItem(CART_KEY);
    this.items = stored ? JSON.parse(stored) : [];
  }

  private saveCart(): void {
    localStorage.setItem(CART_KEY, JSON.stringify(this.items));
    this.updateCartCount(); 
  }

  getCart(): CartItem[] {
    this.loadCart();
    return this.items;
  }

  setCart(newCart: CartItem[]): void {
    this.items = newCart;
    this.saveCart();
  }

  addToCart(productSize: ProductSizeDTO, qty: number = 1): void {
    this.loadCart();

    const existing = this.items.find(
      item => item.productSizeId === productSize.id && item.productId === productSize.product?.id
    );

    if (existing) {
      existing.qty += qty;
      this.saveCart();
    } else {
      this.stockService.getStocksByProductSizeId(productSize.id).subscribe((stocks: StockDTO[]) => {
        const stockIds = stocks.map(s => s.id);

        const newItem: CartItem = {
          productId: productSize.product?.id || '',
          productName: productSize.product?.name || '',
          productImg: productSize.frontUrl || '',
          qty,
          productSizeId: productSize.id,
          productSize: productSize.sizeProd,
          productSizePrice: productSize.price,
          pricePromo: productSize.pricePromo,
          stockIds,
          availableQuantities: stocks.map(s => ({
            stockId: s.id,
            quantity: s.quantity
          })),
          availableQuantity: Math.min(...stocks.map(s => s.quantity))
        };

        this.items.push(newItem);
        this.saveCart();
      });
    }
  }

  removeFromCart(productSizeId: string): void {
    this.loadCart();
    this.items = this.items.filter(item => item.productSizeId !== productSizeId);
    this.saveCart();
  }

  clearCart(): void {
    this.items = [];
    this.saveCart();
  }
}
