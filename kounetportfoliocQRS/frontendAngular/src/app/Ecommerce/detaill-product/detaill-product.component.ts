import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductDTO, ProductSizeDTO } from '../../../app/mesModels/models';
import { ProductService } from '../../../app/Ecommerce/services/produit.service';
import { ProductSizeService } from '../services/product-size.service';
import { CartService } from '../services/cartservice';
import { LikeService } from '../services/like.service';
import { AuthService } from '../../services/AuthService';
import { StockService } from '../services/stock.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './detaill-product.component.html',
  styleUrls: ['./detaill-product.component.css'],
  standalone: false,
})
export class ProductDetailsComponent implements OnInit {
  product: ProductDTO | null = null;
  productSize: ProductSizeDTO | null = null;
  quantity = 1;
  addedMessage = '';
  selectedImageIndex = 0;
  selectedSize = '';
  likeCount = 0;
  liked = false;
  stockCount: number | null = null;

  reviews = [
    {
      avatar: 'assets/img/person/person-m-1.webp',
      name: 'John Doe',
      date: '03/15/2024',
      rating: 5,
      title: 'Super qualité',
      content: 'Produit conforme, très confortable et livraison rapide.'
    },
    // ...autres avis...
  ];

  newReview = {
    rating: 5,
    name: '',
    email: '',
    title: '',
    content: ''
  };

  reviewMessage = '';

  // Pour la galerie d'images
  get images(): string[] {
    if (!this.productSize) return [];
    return [
      this.productSize.frontUrl,
      this.productSize.backUrl,
      this.productSize.leftUrl,
      this.productSize.rightUrl
    ].filter(Boolean);
  }

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private productSizeService: ProductSizeService,
    private cartService: CartService,
    private likeService: LikeService,
    private authService: AuthService,
    private stockService: StockService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productSizeService.getProductSizeById(id).subscribe(size => {
        this.productSize = size;
        this.selectedSize = size.sizeProd;
        if (size.prodId) {
          this.productService.getProductById(size.prodId).subscribe(prod => {
            this.product = prod;
            this.loadLikesCount();
            this.checkIfLiked();
          });
        }
        if (this.productSize?.id) {
          this.stockService.getStocksByProductSizeId(this.productSize.id).subscribe(stocks => {
            this.stockCount = stocks.reduce((sum, stock) => sum + (stock.quantity ?? 0), 0);
          });
        }
      });
    }
  }

  selectImage(idx: number) {
    this.selectedImageIndex = idx;
  }

  selectSize(size: string) {
    this.selectedSize = size;
  }

  incrementQty() {
    this.quantity++;
  }

  decrementQty() {
    if (this.quantity > 1) this.quantity--;
  }

  addToCart() {
    if (!this.productSize) return;
    this.cartService.addToCart(this.productSize, this.quantity);
    this.addedMessage = 'Produit ajouté au panier !';
    setTimeout(() => (this.addedMessage = ''), 1500);
  }

  toggleLike() {
    const customerId = this.authService.getUserId();
    if (!this.productSize?.id || !customerId) return;
    if (this.liked) {
      this.likeService.unlikeProduct(this.productSize.id).subscribe(() => {
        this.liked = false;
        this.loadLikesCount();
      });
    } else {
      this.likeService.likeProduct(this.productSize.id).subscribe(() => {
        this.liked = true;
        this.loadLikesCount();
      });
    }
  }

  private loadLikesCount() {
    if (!this.productSize?.id) return;
    this.likeService.countLikesByProduct(this.productSize.id).subscribe(count => {
      this.likeCount = count;
    });
  }

  private checkIfLiked() {
    const customerId = this.authService.getUserId();
    if (!this.productSize?.id || !customerId) return;
    this.likeService.checkCustomerLiked(this.productSize.id, customerId).subscribe(isLiked => {
      this.liked = isLiked;
    });
  }

  get fiveStars() {
    return [1, 2, 3, 4, 5];
  }

  submitReview() {
    // Ici tu peux envoyer l'avis au backend ou juste l'ajouter localement
    this.reviews.unshift({ ...this.newReview, date: new Date().toLocaleDateString(), avatar: 'assets/img/person/person-m-1.webp' });
    this.reviewMessage = "Merci pour votre avis !";
    this.newReview = { rating: 5, name: '', email: '', title: '', content: '' };
    setTimeout(() => this.reviewMessage = '', 3000);
  }
}