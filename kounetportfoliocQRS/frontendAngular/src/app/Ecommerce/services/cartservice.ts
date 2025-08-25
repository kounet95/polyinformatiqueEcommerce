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

  private cartCountSubject = new BehaviorSubject<number>(0);
  cartCount$ = this.cartCountSubject.asObservable();

  private cartItemsSubject = new BehaviorSubject<CartItem[]>([]);
  cartItems$ = this.cartItemsSubject.asObservable();

  constructor(private stockService: StockService) {
    this.loadCart();
    this.updateCartCount();
    this.updateCartItems();
  }

  ngOnInit(): void {}

  private updateCartCount(): void {
    const count = this.items.reduce((sum, item) => sum + item.qty, 0);
    this.cartCountSubject.next(count);
  }

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
    this.updateCartItems();
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
    // Récupère les stocks pour ce productSize
    this.stockService.getStocksByProductSizeId(productSize.id).subscribe(stocks => {
      const stockIds = stocks.map(s => s.id);
      if (stockIds.length === 0) {
        // Ne pas ajouter au panier si aucun stock
        alert("Ce produit n'est pas disponible en stock.");
        return;
      }
      const cartItem: CartItem = {
        productId: productSize.prodId,
        productName: productSize.product?.name ?? '',
        productImg: productSize.frontUrl,
        qty,
        productSizeId: productSize.id,
        productSize: productSize.sizeProd,
        productSizePrice: productSize.price,
        pricePromo: productSize.pricePromo,
        stockIds, // tableau de string
        availableQuantities: stocks.map(s => ({ stockId: s.id, quantity: s.quantity })),
        availableQuantity: stocks.reduce((sum, s) => sum + s.quantity, 0)
      };
      // Ajoute au panier
      this.items.push(cartItem);
      this.saveCart();
    });
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

  // Pour récupérer tous les stockIds du panier :
  getAllStockIds(): string[] {
    return this.getCart()
      .map(item => item.stockIds)
      .flat();
  }
}