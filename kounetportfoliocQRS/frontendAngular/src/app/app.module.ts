import { APP_INITIALIZER, ApplicationConfig, NgModule } from '@angular/core';
import { BrowserModule, DomSanitizer } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav'; 
import { CarouselModule } from 'ngx-owl-carousel-o';
import { MatTabsModule } from '@angular/material/tabs';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { AcceuilComponent } from './acceuil/acceuil.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AboutComponent } from './about/about.component';
import { ServicesComponent } from './services/services.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { PrincingComponent } from './princing/princing.component';
import { TeamComponent } from './team/team.component';
import { BlogComponent } from './blog/blog.component';
import { ContactComponent } from './contact/contact.component';
import { BoutiqueComponent } from './boutique/boutique.component';
import { CtaSectionComponent } from './cta-section/cta-section.component';
import { CommonModule } from '@angular/common';
import { ArticleRecentComponent } from './article-recent/article-recent.component';
import {MatSortModule} from '@angular/material/sort';
import { NewsComponent } from './monBlog/news/news.component';
import { EventsComponent } from './monBlog/events/events.component';
import { CommentaireComponent } from './monBlog/commentaire/commentaire.component';
import { DomainComponent } from './monBlog/domain/domain.component';
import { TagComponent } from './monBlog/tag/tag.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import { ArticleComponent } from "./monBlog/article/article.component";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import { NewsArticleComponent } from './monBlog/news-article/news-article.component';
import { CreateTagComponent } from './monBlog/create-tag/create-tag.component';
import { CreateDomainComponent } from './monBlog/create-domain/create-domain.component';
import { CreateEventComponent } from './monBlog/create-event/create-event.component';
import { CreateNewsComponent } from './monBlog/create-news/create-news.component';
import { HomeComponent } from './Ecommerce/home/home.component';
import { DetaillProductComponent } from './Ecommerce/detaill-product/detaill-product.component';
import { TrackOrderComponent } from './Ecommerce/track-order/track-order.component';
import { ProductsComponent } from './Ecommerce/products/products.component';
import { CategoryComponent } from './Ecommerce/category/category.component';
import { CategoryCreateComponent } from './Ecommerce/category-create/category-create.component';



function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080',
        realm: 'polyinformatiqueEcommerce',
        clientId: 'frontend'
      },
      initOptions: {
        onLoad: 'check-sso',
        silentCheckSsoRedirectUri:
          window.location.origin + '/assets/silent-check-sso.html'
      }
    });
}



@NgModule({

  declarations: [
    AppComponent,

    HeaderComponent,


    PrincingComponent,
    
    
    BoutiqueComponent,
 
    
    NewsComponent,
    EventsComponent,
    CommentaireComponent,
    DomainComponent,
    TagComponent,
    NewsArticleComponent,
    CreateTagComponent,
    CreateDomainComponent,
    CreateEventComponent,
    CreateNewsComponent,
    CreateEventComponent,
    DetaillProductComponent,
    ProductsComponent,
    CategoryComponent,
    CategoryCreateComponent,
 
   
    
  ],
  imports: [
  
    BlogComponent,
    CtaSectionComponent,
    NavbarComponent,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatGridListModule,
    MatFormFieldModule,
    MatInputModule,
    MatSidenavModule,
    RouterModule,
    FormsModule,
    MatSidenavModule,
    CtaSectionComponent,
    CarouselModule,
    BrowserAnimationsModule,
    CommonModule,
    MatTabsModule,
    MatTabsModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    MatIconModule,
    MatSidenavModule,
    MatPaginatorModule,
    MatSortModule,
    CommonModule,
    CommonModule,
    MatCardModule,
    MatCardModule,
    ArticleComponent,
    KeycloakAngularModule,
    FooterComponent,
],

 providers: [
    {provide : APP_INITIALIZER, useFactory : initializeKeycloak, multi :true, deps : [KeycloakService]}
  ],
  bootstrap: [AppComponent],
})
export class AppModule {

constructor(
    private iconRegistry: MatIconRegistry,
    private sanitizer: DomSanitizer
  ) {
    this.iconRegistry.setDefaultFontSetClass('material-icons');
  }

}