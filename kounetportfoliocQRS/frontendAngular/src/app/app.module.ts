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
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MatChipsModule } from '@angular/material/chips';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { NavbarComponent } from './navbar/navbar.component';

import { MatTableModule } from '@angular/material/table';
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
import { MatBadgeModule } from '@angular/material/badge';
import { MatExpansionModule } from '@angular/material/expansion';
import { ArticleComponent } from "./monBlog/article/article.component";
import {KeycloakAngularModule, KeycloakService} from "keycloak-angular";
import { NewsArticleComponent } from './monBlog/news-article/news-article.component';
import { CreateTagComponent } from './monBlog/create-tag/create-tag.component';
import { CreateDomainComponent } from './monBlog/create-domain/create-domain.component';
import { CreateEventComponent } from './monBlog/create-event/create-event.component';
import { CreateNewsComponent } from './monBlog/create-news/create-news.component';
import {  ProductDetailsComponent } from './Ecommerce/detaill-product/detaill-product.component';
import { CategoryComponent } from './Ecommerce/category/category.component';
import { CategoryCreateComponent } from './Ecommerce/category-create/category-create.component';
import { MatListModule } from '@angular/material/list';
import { MatDividerModule } from '@angular/material/divider';
import { AnnouncementBarComponent } from './Ecommerce/announcement-bar/announcement-bar.component';
import { FormsModule } from '@angular/forms';
import { CreateProductComponent } from './Ecommerce/create-product/create-product.component';
import { SousCategoryCreateComponent } from './Ecommerce/sous-category-create/sous-category-create.component';
import { CreatGroupeSocialComponent } from './Ecommerce/creat-groupe-social/creat-groupe-social.component';
import { AccountComponent } from './Ecommerce/account/account.component';
import { OrderSummaryComponentComponent } from './Ecommerce/order-summary-component/order-summary-component.component';
import { OrderLineComponent } from './Ecommerce/order-line/order-line.component';
import { OrderStatusComponent } from './Ecommerce/order-status/order-status.component';
import { CartComponent } from './Ecommerce/cart/cart.component';
import { OrderCreateComponent } from './Ecommerce/order-create/order-create.component';
import { CreateSupplierComponent } from './Ecommerce/create-spplier/create-spplier.component';
import { CreateProductSizeComponent } from './Ecommerce/create-product-size/create-product-size.component';
import { ProductSizeComponent } from './Ecommerce/product-size/product-size.component';
import { CheckoutComponent } from './Ecommerce/checkout/checkout.component';

import { FaqComponent } from './Ecommerce/faq/faq.component';
import { PrivacyComponent } from './Ecommerce/privacy/privacy.component';
import { AddressFormComponent } from './Ecommerce/address-form/address-form.component';
import { RegisterComponent } from './Ecommerce/register/register.component';
import { LoginComponent } from './Ecommerce/login/login.component';
import { ConditionsComponent } from './Ecommerce/conditions/conditions.component';
import { LikeProductComponent } from './Ecommerce/like-product/like-product.component';
import { DashboardComponent } from './Ecommerce/dashboard/dashboard.component';



function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://keycloak:8080',
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
    ProductDetailsComponent,
    CategoryCreateComponent,
    CreateProductComponent,
    SousCategoryCreateComponent,
    CreatGroupeSocialComponent,
    OrderSummaryComponentComponent,
    OrderLineComponent,
    OrderStatusComponent,
    OrderCreateComponent,
    CreateProductSizeComponent,
    ProductSizeComponent,
    PrivacyComponent,

    LoginComponent,
    ConditionsComponent,
    DashboardComponent

   
    
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
    MatCardModule,
    MatCardModule,
    ArticleComponent,
    KeycloakAngularModule,
    FooterComponent,
    MatListModule,
    MatDividerModule,
    MatExpansionModule,
    MatProgressBarModule,
    MatFormFieldModule,
    MatSortModule,
    MatIconModule,
    MatSelectModule,
    MatOptionModule,
    MatCardModule,
    MatChipsModule,
    MatTabsModule,
    MatCardModule,
    MatTableModule,
    MatBadgeModule,
    AnnouncementBarComponent
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