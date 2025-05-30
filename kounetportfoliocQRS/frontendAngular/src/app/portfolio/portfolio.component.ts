import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { KeycloakAngularModule } from 'keycloak-angular';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  standalone: true,
  styleUrls: ['./portfolio.component.css'],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatGridListModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
    MatTabsModule,
    RouterModule,
    CarouselModule,
    KeycloakAngularModule
  ]
})
export class PortfolioComponent {
  portfolio = [
    { img: 'assets/img/01_Home.png', title: 'Liveeducation' , url : 'https://isbbethesda.com/'},
    { img: 'assets/img/Epharmacy_logo.png', title: ' Medicare ', url : 'https://servicesproviders.org/Cinsol/index.php' },
    { img: 'assets/img/le_continent.png', title: 'Lecontinent' , url : 'http://localhost:4200/home'},
  ];
}