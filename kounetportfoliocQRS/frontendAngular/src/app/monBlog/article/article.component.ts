import { Component, OnInit, ViewChild } from '@angular/core';
import { ArticleModel } from '../../mesModels/models';          
import { Router, RouterModule } from '@angular/router';
import { ArticlesService } from '../../messervices/blog/articles.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatCardModule } from '@angular/material/card';         // <-- Ajoute ceci !
import { CommonModule } from '@angular/common';                 // <-- Et ceci !
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css'],
  standalone: true,
  imports: [
    CommonModule,         
    MatCardModule,        
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatListModule,
    RouterModule,
     HttpClientModule,
  ]
})
export class ArticleComponent implements OnInit {
  public articles: ArticleModel[] = [];
  public displayedColumns = [
    "id",
    "title",
    "content",
    "urlMedia",
    "createdAt",
    "authorId",
    "domainId",
    "tagIds",
    "commentIds"
  ];
  public dataSource: any;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private articleService: ArticlesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.articleService.getAllArticles().subscribe({
      next: (data) => {
        this.articles = data;
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  goToDetails(article: ArticleModel) {
    this.router.navigate(['/admin/plus', article.id]);
  }
}