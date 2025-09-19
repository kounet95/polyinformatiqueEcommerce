import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTabsModule } from '@angular/material/tabs';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { RouterModule } from '@angular/router';
import { KeycloakProfile } from 'keycloak-js';
import { CartService } from '../Ecommerce/services/cartservice';
import { LikeProductComponent } from '../Ecommerce/like-product/like-product.component';
import { LikeService } from '../Ecommerce/services/like.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatListModule,
    MatCardModule,
    MatFormFieldModule,
    MatGridListModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
    MatTabsModule,
    KeycloakAngularModule,
    CarouselModule,
    RouterModule,
  ]
})
export class NavbarComponent implements OnInit {
  cartCount = 0;
  cartItems: any[] = [];
  cartTotal = 0;
  title = 'ecom-app-angular';
  isMenuOpen = false;
  isLoggedIn = false;
  public profile: KeycloakProfile | null = null;
  likeCount = 0;
  likedItems: any[] = [];

constructor(
    private keycloakService: KeycloakService,
    private cartService: CartService,
    private likeService: LikeService
  ) {}

  async ngOnInit() {
    this.isLoggedIn = await this.keycloakService.isLoggedIn();

    if (this.isLoggedIn) {
      this.keycloakService.loadUserProfile()
        .then(profile => this.profile = profile)
        .catch(() => this.profile = null);
    }

    this.cartService.cartCount$.subscribe(count => {
      this.cartCount = count;
    });

    this.cartService.cartItems$.subscribe(items => {
      this.cartItems = items;
      this.cartTotal = this.cartItems.reduce(
        (total, item) => total + (item.productSizePrice * item.qty), 0
      );
      // Rafraîchir les likes pour tous les produits du panier
      this.cartItems.forEach(item => {
        this.likeService.refreshLikes(item.productId);
      });
    });

    this.likeService.likeCount$.subscribe(count => {
      this.likeCount = count;
    });

    this.likeService.likedItems$.subscribe(items => {
      this.likedItems = items;
    });

    // Rafraîchir les likes pour un produit par défaut (ex: premier produit du panier si présent)
    if (this.cartItems.length > 0) {
      this.likeService.refreshLikes(this.cartItems[0].productId);
    }
    // Sinon, ne rien faire
  }

  removeFromCart(productSizeId: string) {
    this.cartService.removeFromCart(productSizeId);
  }

  removeFromLikes(productId: string) {
    this.likeService.unlikeProduct(productId);
  }

  async handleLogin() {
    await this.keycloakService.login({ redirectUri: window.location.origin });
    this.isLoggedIn = true;
    this.isMenuOpen = true;
  }

  async handleLogout() {
    await this.keycloakService.logout(window.location.origin);
    this.isLoggedIn = false;
    this.profile = null;
    this.isMenuOpen = false;
  }
}
