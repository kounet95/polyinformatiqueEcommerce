import { Component, OnInit } from '@angular/core';
import { CartService, CartItem } from '../services/cartservice';
import { ProductService } from '../services/produit.service';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  standalone:false,
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  couponCode: string = '';
  shippingOption: string = 'standard';
  subtotal = 0;
  tax = 0;
  discount = 0;
  total = 0;

  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit() {
    // Je veux cherher les infos produits depuis mon backend pour obtenir les informations actuel de mes produit
    const cart = this.cartService.getCart();
    if (cart.length > 0) {
      const requests = cart.map(item =>
        this.productService.getProductById(item.id).pipe(
          map(freshProduct => ({
            ...freshProduct,
            qty: item.qty
          }))
        )
      );
      forkJoin(requests).subscribe(freshCartItems => {
        this.cartItems = freshCartItems;
        this.recalculate();
        //remettre le panier à jour dans le service :
        this.cartService.setCart(this.cartItems);
      });
    } else {
      this.cartItems = [];
      this.recalculate();
    }
  }

  incrementQty(item: CartItem) {
    item.qty++;
    this.updateCart();
  }

  decrementQty(item: CartItem) {
    if (item.qty > 1) {
      item.qty--;
      this.updateCart();
    }
  }

  removeFromCart(productId: string) {
    this.cartService.removeFromCart(productId);
    this.cartItems = this.cartService.getCart();
    this.recalculate();
  }

  clearCart() {
    this.cartService.clearCart();
    this.cartItems = [];
    this.recalculate();
  }

  updateCart() {
    this.cartService.setCart(this.cartItems);
    this.recalculate();
  }

  applyCoupon() {
    // regle pour mes promos : -$10 si le code est "PROMO10"
    if (this.couponCode === 'PROMO10') {
      this.discount = 10;
    } else {
      this.discount = 0;
    }
    this.recalculate();
  }

  recalculate() {
    this.subtotal = this.cartItems.reduce((sum, item) => sum + (item.price || 0) * item.qty, 0);
    // Calcul shipping
    let shipping = 0;
    if (this.shippingOption === 'standard') shipping = 4.99;
    else if (this.shippingOption === 'express') shipping = 12.99;
    else if (this.shippingOption === 'free' && this.subtotal >= 300) shipping = 0;
    else if (this.shippingOption === 'free' && this.subtotal < 300) shipping = 15; // fallback

    this.tax = this.subtotal * 0.1; // exemple : 10%
    this.total = this.subtotal + shipping + this.tax - this.discount;
  }

  proceedToCheckout() {
    this.router.navigate(['/order/create']);
  }

  continueShopping() {
    this.router.navigate(['/products']);
  }
}