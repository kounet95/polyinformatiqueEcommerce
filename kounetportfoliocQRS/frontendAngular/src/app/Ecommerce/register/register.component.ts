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
import { KeycloakProfile } from 'keycloak-js';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
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
export class RegisterComponent implements OnInit {

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
