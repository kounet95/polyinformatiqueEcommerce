import { Component, OnInit } from '@angular/core';
import { CartService } from '../services/cartservice';
import { Router } from '@angular/router';
import { CartItem } from '../../mesModels/models';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  standalone: false,
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
    private router: Router
  ) {}

  ngOnInit() {
    this.cartItems = this.cartService.getCart();
    this.recalculate();
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

  removeItem(item: CartItem) {
    this.cartService.removeFromCart(item.productSizeId);
    this.cartItems = this.cartService.getCart();
    this.recalculate();
  }

  updateCart() {
    this.cartService.setCart(this.cartItems);
    this.recalculate();
  }

  applyCoupon() {
    if (this.couponCode === 'PROMO10') {
      this.discount = 10;
    } else {
      this.discount = 0;
    }
    this.recalculate();
  }

  recalculate() {
    this.subtotal = this.cartItems.reduce((sum, item) =>
      sum + ((item.pricePromo ?? item.productSizePrice) * item.qty), 0
    );
    let shipping = 0;
    if (this.shippingOption === 'standard') shipping = 4.99;
    else if (this.shippingOption === 'express') shipping = 12.99;
    else if (this.shippingOption === 'free' && this.subtotal >= 300) shipping = 0;
    else if (this.shippingOption === 'free' && this.subtotal < 300) shipping = 15;

    this.tax = this.subtotal * 0.1;
    this.total = this.subtotal + shipping + this.tax - this.discount;
  }

  proceedToCheckout() {
    this.router.navigate(['/checkout']);
  }

  continueShopping() {
    this.router.navigate(['/home']);
  }
}