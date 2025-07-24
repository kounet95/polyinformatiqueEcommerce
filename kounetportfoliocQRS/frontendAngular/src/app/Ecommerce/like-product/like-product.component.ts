import { Component, Input, OnInit } from '@angular/core';
import { LikeService } from '../services/like.service';
import { AuthService } from '../../services/AuthService';
import { CustomerEcommerceDTO } from '../../mesModels/models';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-like-product',
  templateUrl: './like-product.component.html',
  styleUrls: ['./like-product.component.css']
})
export class LikeProductComponent implements OnInit {
  message = '';
  loading = false;

  customer: CustomerEcommerceDTO = {
    id: '',
    firstname: '',
    lastname: '',
    email: '',
    phone: '',
    addressId: '',
    createdAt: ''
  };

  @Input() productId!: string;
  isLiked = false;
  likeCount = 0;

  constructor(
    private likeService: LikeService,
    private authService: AuthService,
    private customerService: CustomerService
  ) {}

  ngOnInit(): void {
    this.loadLikesCount();
    this.checkIfLiked();
    this.loadCustomer();
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
    this.likeService.likeProduct(this.productId).subscribe(() => {
      this.isLiked = true;
      this.loadLikesCount();
    });
  }

  unlike(): void {
    this.likeService.unlikeProduct(this.productId).subscribe(() => {
      this.isLiked = false;
      this.loadLikesCount();
    });
  }

  private loadLikesCount(): void {
    this.likeService.countLikesByProduct(this.productId).subscribe(count => {
      this.likeCount = count;
    });
  }

  private checkIfLiked(): void {
    const customerId = this.authService.getUserId(); 
    if (customerId) {
      this.likeService.checkCustomerLiked(this.productId, customerId).subscribe(isLiked => {
        this.isLiked = isLiked;
      });
    }
  }
}
