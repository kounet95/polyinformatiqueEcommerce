import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-article-recent',
  templateUrl: './article-recent.component.html',
  styleUrl: './article-recent.component.css',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatListModule,
    RouterModule,
    CommonModule,         
    MatCardModule,        
   
  ]
})
export class ArticleRecentComponent {
blogs = [
    {
      image: 'assets/images/blog1.jpg',
      alt: 'Personne travaillant sur ordinateur dans un bureau'
    },
    {
      image: 'assets/images/blog2.jpg',
      alt: 'Personne utilisant un ordinateur portable'
    },
    {
      image: 'assets/images/blog3.jpg',
      alt: 'Collaboration autour de table avec ordinateurs'
    }
  ];
}
