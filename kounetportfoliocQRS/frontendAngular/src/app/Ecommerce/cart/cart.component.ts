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
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit() {
    const cart = this.cartService.getCart();
    if (cart.length > 0) {
      const requests = cart.map(item =>
        this.productService.getProductById(item.id).pipe(
          map(freshProduct => {
            let chosenSize = null;
            if (item.productSizeId) {
              chosenSize = freshProduct.productSizes?.find(
                (size: any) => size.id === item.productSizeId
              );
            } else if (item.productSize) {
              chosenSize = freshProduct.productSizes?.find(
                (size: any) => size.sizeProd === item.productSize
              );
            }
            if (!chosenSize && freshProduct.productSizes?.length) {
              chosenSize = freshProduct.productSizes[0];
            }
            return {
              ...freshProduct,
              qty: item.qty,
              productSizeId: chosenSize?.id ?? item.productSizeId,
              productSize: chosenSize?.sizeProd ?? item.productSize,
              productSizePrice: chosenSize?.price ?? 0, // <-- LA BONNE PROPRIÉTÉ
            };
          })
        )
      );
      forkJoin(requests).subscribe(freshCartItems => {
        this.cartItems = freshCartItems;
        this.recalculate();
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

  removeFromCart(productId: string, productSizeId?: string) {
    this.cartService.removeFromCart(productId, productSizeId);
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
    if (this.couponCode === 'PROMO10') {
      this.discount = 10;
    } else {
      this.discount = 0;
    }
    this.recalculate();
  }

  recalculate() {
    this.subtotal = this.cartItems.reduce((sum, item) => sum + (item.productSizePrice || 0) * item.qty, 0);
    let shipping = 0;
    if (this.shippingOption === 'standard') shipping = 4.99;
    else if (this.shippingOption === 'express') shipping = 12.99;
    else if (this.shippingOption === 'free' && this.subtotal >= 300) shipping = 0;
    else if (this.shippingOption === 'free' && this.subtotal < 300) shipping = 15;

    this.tax = this.subtotal * 0.1;
    this.total = this.subtotal + shipping + this.tax - this.discount;
  }

  proceedToCheckout() {
    this.router.navigate(['/order/create']);
  }

  continueShopping() {
    this.router.navigate(['/products']);
  }
}