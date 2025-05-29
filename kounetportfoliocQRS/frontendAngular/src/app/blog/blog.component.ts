import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router, NavigationEnd, RouterModule } from '@angular/router';


interface Breadcrumb {
  label: string;
  url: string;
}

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css'],
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
export class BlogComponent implements OnInit {
  breadcrumbs: Breadcrumb[] = [];
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

  constructor(private router: Router) {}

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.buildBreadcrumb();
      }
    });
    this.buildBreadcrumb();
  }

  buildBreadcrumb() {
    this.breadcrumbs = [];
    let url = '';
    const parts = this.router.url.split('/').filter(Boolean);
    parts.forEach((part, idx) => {
      url += '/' + part;
      this.breadcrumbs.push({
        label: part,
        url: url
      });
    });
    this.breadcrumbs.unshift({ label: 'Home', url: '/' });
  }
}