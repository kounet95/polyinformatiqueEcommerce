import { CommonModule, ViewportScroller } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router, RouterModule } from '@angular/router';

import { ServicesComponent } from '../services/services.component';
import { PortfolioComponent } from '../portfolio/portfolio.component';
import { TeamComponent } from '../team/team.component';
import { ContactComponent } from '../contact/contact.component';
import { ArticleComponent } from '../monBlog/article/article.component';
import { AboutComponent } from "../about/about.component";
import { ArticleRecentComponent } from "../article-recent/article-recent.component";

@Component({
  selector: 'app-acceuil',
  templateUrl: './acceuil.component.html',
  styleUrls: ['./acceuil.component.css'],
  standalone: true, 
  imports: [CommonModule,
    RouterModule, AboutComponent,
   MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatSidenavModule,
    MatListModule,
    RouterModule,
    CommonModule,         
    MatCardModule,  ]
})
export class AcceuilComponent implements OnInit{

services = [
    { icon: 'build', title: 'Conception de sites web', desc: 'Sites web modernes et performants', color: 'primary' },
    { icon: 'build', title: 'Conception d\'applications web', desc: 'Applications web modernes et performants', color: 'primary' },
    { icon: 'security', title: '', desc: 'Preparations aux certifications professionnelles informatiques(CCNA, CCNP, AWS, AZURE)', color: 'primary' },
    { icon: 'brush', title: 'Commerce vestimentaire', desc: 'Vetements culturels', color: 'warn' },
  ];

  constructor(){

  }
  
  ngOnInit(): void {
      
  }
}
