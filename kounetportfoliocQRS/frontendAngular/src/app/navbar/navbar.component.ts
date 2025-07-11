import { Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { CommonModule, ViewportScroller } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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

import { Router, RouterModule } from '@angular/router';
import { AppRoutingModule } from '../app-routing.module';
import { KeycloakProfile } from 'keycloak-js';

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
]
})
export class NavbarComponent  implements OnInit{
  title = 'ecom-app-angular';
  isMenuOpen = false;
  isLoggedIn = false;
public profile: KeycloakProfile | null = null;
  constructor(public keycloakService : KeycloakService) {
  }

    ngOnInit() {
    this.keycloakService.loadUserProfile().then(profile => {
      this.profile = profile;
    }).catch(() => {
      this.profile = null;
    });
  }

  async handleLogin() {
    await this.keycloakService.login({
      redirectUri: window.location.origin
      
    });

    this.isMenuOpen = true;
  }

  handleLogout(){
    this.keycloakService.logout(window.location.origin);
     this.isLoggedIn = false
  }


}