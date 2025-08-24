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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
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
    BrowserAnimationsModule,
    KeycloakAngularModule,
    CarouselModule,
    RouterModule,
    LikeProductComponent
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
    // Vérifier si l’utilisateur est déjà connecté
    this.isLoggedIn = await this.keycloakService.isLoggedIn();

    if (this.isLoggedIn) {
      this.keycloakService.loadUserProfile()
        .then(profile => this.profile = profile)
        .catch(() => this.profile = null);
    }

    // Suivi du compteur
    this.cartService.cartCount$.subscribe(count => {
      this.cartCount = count;
    });

    // Suivi des articles
    this.cartService.cartItems$.subscribe(items => {
      this.cartItems = items;
      this.cartTotal = this.cartItems.reduce(
        (total, item) => total + (item.productSizePrice * item.qty), 0
      );
    });

    // Suivi du compteur de likes
    this.likeService.likeCount$.subscribe(count => {
      this.likeCount = count;
    });

    // Suivi des articles likés
    this.likeService.likedItems$.subscribe(items => {
      this.likedItems = items;
    });

    // Rafraîchir les likes au démarrage
    this.likeService.refreshLikes();
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
