import { Injectable, OnInit } from '@angular/core';
import { ProductSizeDTO, CartItem, StockDTO } from '../../mesModels/models';
import { StockService } from './stock.service';
import { Observable } from 'rxjs';

const CART_KEY = 'cart_items';

@Injectable({
  providedIn: 'root'
})
export class CartService implements OnInit {
  private items: CartItem[] = [];

  constructor(private stockService: StockService) {
    this.loadCart();
  }

  ngOnInit(): void {
    // Ne pas throw d'erreur ici
    this.loadStockedCart();
  }

  // Charge le panier depuis localStorage et récupère les stocks associés
  loadStockedCart(): void {
    this.loadCart();

    this.items.forEach(item => {
      if (item.stockIds && item.stockIds.length > 0) {
        // Initialise un tableau pour stocker les quantités disponibles
        (item as any).availableQuantities = [];

        item.stockIds.forEach(stockId => {
          // Récupère le stock par stockId
          this.stockService.getStockById(stockId).subscribe(stock => {
            console.log(`Stock réel pour ${stockId}:`, stock);

            // Stocke la quantité disponible par stockId
            (item as any).availableQuantities.push({
              stockId: stock.id,
              quantity: stock.quantity
            });

            // Optionnel : calculer la quantité disponible minimale parmi les stocks liés
            (item as any).availableQuantity = Math.min(
              ...( (item as any).availableQuantities.map((s: any) => s.quantity) )
            );
          });
        });
      }
    });
  }

  private loadCart(): void {
    const stored = localStorage.getItem(CART_KEY);
    this.items = stored ? JSON.parse(stored) : [];
  }

  private saveCart(): void {
    localStorage.setItem(CART_KEY, JSON.stringify(this.items));
  }

  getCart(): CartItem[] {
    this.loadCart(); // Toujours recharger pour avoir la dernière version
    return this.items;
  }

  setCart(newCart: CartItem[]): void {
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
      pricePromo: productSize.pricePromo,
      stockIds: productSize.stockIds && productSize.stockIds.length > 0 ? productSize.stockIds : [],  
    });
  }

  this.saveCart();
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
