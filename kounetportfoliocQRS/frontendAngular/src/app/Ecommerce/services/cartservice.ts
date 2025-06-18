import { Injectable } from '@angular/core';
import { ProductDTO } from '../../mesModels/models';

export interface CartItem extends ProductDTO {
  qty: number;
  productSizeId?: string;    // l'id de la taille choisie
  productSize?: string;      // le libellé ou nom de la taille choisie
  productSizePrice?: number; // le prix précis de la taille choisie
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

  addToCart(product: ProductDTO, qty: number = 1, productSizeId?: string, productSize?: string): void {
    // Si le produit existe déjà et la même taille, on incrémente la quantité
    const existing = this.items.find(
      item => item.id === product.id && item.productSizeId === productSizeId
    );
    let price = 0;
    if (productSizeId && product.productSizes) {
      const match = product.productSizes.find((sz: any) => sz.id === productSizeId);
      price = match ? match.price : 0;
    } else if (product.productSizes && product.productSizes.length > 0) {
      price = product.productSizes[0].price;
      productSizeId = product.productSizes[0].id;
      productSize = product.productSizes[0].sizeProd;
    }
    if (existing) {
      existing.qty += qty;
    } else {
      this.items.push({
        ...product,
        qty,
        productSizeId,
        productSize,
        productSizePrice: price
      });
    }
  }

  removeFromCart(productId: string, productSizeId?: string) {
    // Retire seulement la bonne taille si précisée
    this.items = this.items.filter(
      item => !(item.id === productId && (!productSizeId || item.productSizeId === productSizeId))
    );
  }

  clearCart() {
    this.items = [];
  }
}