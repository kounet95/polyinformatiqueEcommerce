import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { AnnouncementBarComponent } from '../announcement-bar/announcement-bar.component';
import { KeycloakService } from 'keycloak-angular';
import { CartService } from '../services/cartservice';
import { LikeService } from '../services/like.service';
import { KeycloakProfile } from 'keycloak-js';
import { OrderDTO } from '../../mesModels/models';
import { OrderService } from '../services/order.service';
import { AuthService } from '../../services/AuthService';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatSnackBarModule,
    MatTableModule,
    MatExpansionModule,
    RouterModule,
    FormsModule,
    MatListModule,
    AnnouncementBarComponent
  ],
})
export class AccountComponent implements OnInit {
  cartCount = 0;
  cartItems: any[] = [];
  ordersDTO: OrderDTO[] = [];
  cartTotal = 0;
  title = 'ecom-app-angular';
  isMenuOpen = false;
  isLoggedIn = false;
  public profile: KeycloakProfile | null = null;
  likeCount = 0;
  likedItems: any[] = [];
  laLettre = 'A';
  nbrCommande = 3;
 

  menu = [
    { icon: 'inventory_2', label: 'Orders', count: 1, selected: true },
    { icon: 'favorite_border', label: 'Wishlist' },
    { icon: 'credit_card', label: 'Payment methods' },
    { icon: 'star_border', label: 'My reviews' },
    { icon: 'person_outline', label: 'Personal info' },
    { icon: 'place', label: 'Addresses' },
    { icon: 'notifications_none', label: 'Notifications' },
  ];

  service = [
    { icon: 'help_outline', label: 'Help center' },
    { icon: 'description', label: 'Terms and conditions' },
    { icon: 'logout', label: 'Log out', color: 'warn' },
  ];

  orders = [
    {
      id: '78A6431D409',
      date: '02/15/2025',
      status: 'In progress',
      statusColor: 'accent',
      total: 2105.9,
      products: [
        { image: 'bag.png' },
        { image: 'chair.png' },
        { image: 'sunglasses.png' }
      ]
    },
    {
      id: '47H76G09F33',
      date: '12/10/2024',
      status: 'Delivered',
      statusColor: 'primary',
      total: 360.75,
      products: [{ image: 'jacket.png' }]
    },
    {
      id: '502TR872W2',
      date: '11/05/2024',
      status: 'Delivered',
      statusColor: 'primary',
      total: 4268.0,
      products: [
        { image: 'heels.png' },
        { image: 'backpack.png' },
        { image: 'jacket2.png' }
      ],
      details: {
        date: '11/05/2024',
        payment: 'Credit Card (**** 4589)',
        items: [
          { name: 'Quis nostrud exercitation', sku: 'PRD-005', qty: 2, price: 1299.99, image: 'heels.png' },
          { name: 'Ullamco laboris nisi', sku: 'PRD-006', qty: 1, price: 799.99, image: 'backpack.png' },
          { name: 'Aliquip ex ea commodo', sku: 'PRD-007', qty: 3, price: 449.99, image: 'jacket2.png' }
        ]
      },
      summary: {
        subtotal: 3899.94,
        shipping: 29.99,
        tax: 338.07,
        total: 4268.0,
        shippingAddress: {
          street: '456 Business Ave',
          suite: 'Suite 200',
          city: 'San Francisco, CA 94107',
          country: 'United States',
        },
        shippingMethod: 'Premium Delivery (1-2 business days)'
      }
    }
  ];

  selectedOrder = this.orders.length > 2 ? this.orders[2] : null;

  constructor(
    private keycloakService: KeycloakService,
    private cartService: CartService,
    private likeService: LikeService,
    private orderService: OrderService,
  private authService: AuthService
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoggedIn = await this.keycloakService.isLoggedIn();

    if (this.isLoggedIn) {
      try {
        this.profile = await this.keycloakService.loadUserProfile();
        this.laLettre = this.profile.firstName ? this.profile.firstName.charAt(0).toUpperCase() : '';
      } catch {
        this.profile = null;
      }
    }

    this.cartService.cartCount$.subscribe(count => {
      this.cartCount = count;
    });

    this.cartService.cartItems$.subscribe(items => {
      this.cartItems = items;
      this.cartTotal = this.cartItems.reduce(
        (total, item) => total + item.productSizePrice * item.qty,
        0
      );
      // Rafraîchir les likes pour tous les produits du panier
      this.cartItems.forEach(item => {
        this.likeService.refreshLikes(item.productSizeId);
      });
    });

    this.likeService.likeCount$.subscribe(count => {
      this.likeCount = count;
    });

    this.likeService.likedItems$.subscribe(items => {
      this.likedItems = items;
    });
    this.loadOrderDetails();
  }

  loadOrderDetails(): void {
    const customerId = this.authService.getUserId();

    if (customerId) {
      this.orderService.getCustomerOrders(customerId).subscribe(order => {
        this.ordersDTO = Array.isArray(order) ? order : [order];
        console.log('OrdersDTO:', this.ordersDTO);
      });
    } else {
      console.error('Customer ID is null. Cannot load orders.');
    }
  }


}
