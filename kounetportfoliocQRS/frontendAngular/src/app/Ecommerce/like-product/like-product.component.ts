import { Component, Input, OnInit } from '@angular/core';
import { LikeService } from '../services/like.service';
import { AuthService } from '../../services/AuthService';
import { CustomerEcommerceDTO, ProductDTO, ProductSizeDTO, LikeDTO } from '../../mesModels/models';
import { CustomerService } from '../services/customer.service';
import { ProductSizeService } from '../services/product-size.service';

@Component({
  selector: 'app-like-product',
  templateUrl: './like-product.component.html',
  styleUrls: ['./like-product.component.css']
})
export class LikeProductComponent implements OnInit {
  message = '';
  loading = false;
  product: ProductDTO | null = null;
  productSize: ProductSizeDTO | null = null;
  likedProductSizeDTOs: ProductSizeDTO[] = []; 
  customer: CustomerEcommerceDTO = {
    id: '',
    firstname: '',
    lastname: '',
    email: '',
    phone: '',
    addressId: '',
    createdAt: ''
  };

  @Input() productSizeId!: string;
  isLiked = false;
  likeCount = 0;
  likedProducts: ProductSizeDTO[] = [];
  productLikes: LikeDTO[] = []; // Liste des likes pour un produit (par productSizeId)

  constructor(
    private likeService: LikeService,
    private authService: AuthService,
    private customerService: CustomerService,
    private productSizeService: ProductSizeService,
  ) {}

  ngOnInit(): void {
    this.loadLikesCount();
    this.checkIfLiked();
    this.loadCustomer();
    this.loadLikedProductSizes(); // Récupère tous les produits likés par le user connecté
    this.loadProductLikes();      // Récupère tous les likes pour ce productSizeId
  }

  // Charge les ProductSize likés par l'utilisateur connecté
  loadLikedProductSizes() {
    const customerId = this.authService.getUserId();
    if (customerId) {
      this.likeService.getLikesByCustomer(customerId).subscribe(likes => {
        // likes: LikeDTO[] avec .productId = ID du ProductSize
        const ids = likes
          .map(like => like.productId)
          .filter((id, idx, arr) => !!id && arr.indexOf(id) === idx); // ids uniques et non vides

        this.likedProductSizeDTOs = [];
        ids.forEach(id => {
          this.productSizeService.getProductSizeById(id).subscribe(ps => {
            if (ps) this.likedProductSizeDTOs.push(ps);
          });
        });
      });
    }
  }

  // Charge la liste des likes pour ce productSizeId (tous les utilisateurs ayant liké ce ProductSize)
  private loadProductLikes() {
    if (this.productSizeId) {
      this.likeService.getLikesByProduct(this.productSizeId).subscribe(likes => {
        this.productLikes = likes;
      });
    }
  }

  loadCustomer() {
    const userId = this.authService.getUserId();
    if (userId) {
      this.customerService.getCustomerById(userId).subscribe({
        next: (customer) => {
          if (customer) {
            this.customer = customer;
          }
        },
        error: () => {
          this.message = "Impossible de charger les informations client.";
        }
      });
    }
  }

  like(): void {
    if (!this.productSizeId) return;
    this.likeService.likeProduct(this.productSizeId).subscribe(() => {
      this.isLiked = true;
      this.loadLikesCount();
      this.loadProductLikes();
      this.loadLikedProductSizes(); // refresh la liste des produits likés
    });
  }

  unlike(): void {
    this.likeService.unlikeProduct(this.productSizeId).subscribe(() => {
      this.isLiked = false;
      this.loadLikesCount();
      this.loadProductLikes();
      this.loadLikedProductSizes(); // refresh la liste des produits likés
    });
  }

  private loadLikesCount(): void {
    if (!this.productSizeId) return;
    this.likeService.countLikesByProduct(this.productSizeId).subscribe(count => {
      this.likeCount = count;
    });
  }

  private checkIfLiked(): void {
    const customerId = this.authService.getUserId();
    if (customerId) {
      this.likeService.checkCustomerLiked(this.productSizeId, customerId).subscribe(isLiked => {
        this.isLiked = isLiked;
      });
    }
  }
}