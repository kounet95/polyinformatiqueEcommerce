import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductDTO }     from '../../../app/mesModels/models';
import { ProductService } from '../../../app/Ecommerce/services/produit.service';
import { CartService }    from '../../../app/Ecommerce/services/cartservice';

@Component({
  selector: 'app-product-details',
  templateUrl: './detaill-product.component.html',
  styleUrls: ['./detaill-product.component.css'],
  standalone:false,
})
export class ProductDetailsComponent implements OnInit {
   product: ProductDTO | null = null;
  quantity: number = 1;
  addedMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productService.getProductById(id).subscribe(prod => {
        this.product = prod;
      });
    }
  }

  incrementQty() {
    this.quantity++;
  }

  decrementQty() {
    if (this.quantity > 1) this.quantity--;
  }

  addToCart() {
    if (!this.product) return;
    this.cartService.addToCart(this.product, this.quantity);
    this.addedMessage = 'Produit ajouté au panier !';
    setTimeout(() => (this.addedMessage = ''), 1500);
  }
}