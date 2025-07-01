import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './about/about.component';


import { BlogComponent } from './blog/blog.component';
import { ContactComponent } from './contact/contact.component';
import { PrincingComponent } from './princing/princing.component';
import { ArticleComponent } from './monBlog/article/article.component';
import { NewsArticleComponent } from './monBlog/news-article/news-article.component';
import { AuthGuard } from './guards/auth.guard';
import { AcceuilComponent } from './acceuil/acceuil.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { TeamComponent } from './team/team.component';
import { ServicesComponent } from './services/services.component';
import { HomeComponent } from './Ecommerce/home/home.component';
import { ProductDetailsComponent } from './Ecommerce/detaill-product/detaill-product.component';
import { TrackOrderComponent } from './Ecommerce/track-order/track-order.component';
import { CategoryComponent } from './Ecommerce/category/category.component';
import { CategoryCreateComponent } from './Ecommerce/category-create/category-create.component';
import { CreateProductComponent } from './Ecommerce/create-product/create-product.component';
import { SousCategoryCreateComponent } from './Ecommerce/sous-category-create/sous-category-create.component';
import { ProductComponent } from './Ecommerce/products/products.component';
import { CreatGroupeSocialComponent } from './Ecommerce/creat-groupe-social/creat-groupe-social.component';
import { AccountComponent } from './Ecommerce/account/account.component';
import { OrderSummaryComponentComponent } from './Ecommerce/order-summary-component/order-summary-component.component';
import { CartComponent } from './Ecommerce/cart/cart.component';
import { OrderComponent } from './Ecommerce/order/order.component';
import { OrderCreateComponent } from './Ecommerce/order-create/order-create.component';
import { CreateSupplierComponent } from './Ecommerce/create-spplier/create-spplier.component';
import { CreateProductSizeComponent } from './Ecommerce/create-product-size/create-product-size.component';
import { CheckoutComponent } from './Ecommerce/checkout/checkout.component';

import { CreatCustomerComponent } from './Ecommerce/creat-customer/creat-customer.component';
import { CreatStockComponent } from './Ecommerce/creat-stock/creat-stock.component';
import { FaqComponent } from './Ecommerce/faq/faq.component';
import { PrivacyComponent } from './Ecommerce/privacy/privacy.component';
import { AddressFormComponent } from './Ecommerce/address-form/address-form.component';
import { RegisterComponent } from './Ecommerce/register/register.component';
import { LoginComponent } from './Ecommerce/login/login.component';
import { ConditionsComponent } from './Ecommerce/conditions/conditions.component';


const routes: Routes = [
  { path: '', loadComponent: () => import('./acceuil/acceuil.component').then(m => m.AcceuilComponent) },
  { path: 'about', component: AboutComponent },
  { path: 'account', component: AccountComponent },
  { path: 'articles', component: ArticleComponent },
  { path: 'blog', component: BlogComponent },
  { path: 'cart', component: CartComponent },
  { path: 'category', component: CategoryComponent },
  { path: 'create-category', component: CategoryCreateComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'commentaire', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'contact', loadComponent: () => import('./contact/contact.component').then(m => m.ContactComponent) },
  { path: 'conditions', component: ConditionsComponent },
  { path: 'create-address', component: AddressFormComponent },
  { path: 'create-domain', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'create-event', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'create-news', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'create-product', component: CreateProductComponent },

  { path: 'create-product-size', component: CreateProductSizeComponent , canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'create-stock', component: CreatStockComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] }},
  { path: 'create-social-groupe', component: CreatGroupeSocialComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'create-supplier', component: CreateSupplierComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'create-tag', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'home', component: HomeComponent },
  { path: 'news-article', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },

  { path: 'order', component: OrderCreateComponent },
  { path: 'order-summary', component: OrderSummaryComponentComponent },

  { path: 'portfolio',component: PortfolioComponent},
  { path: 'pricing', component: PrincingComponent },
  { path: 'privacy', component: PrivacyComponent },
  { path: 'products', component: ProductComponent },
  { path: 'product', component: ProductDetailsComponent },

  { path: 'product/:id', component: ProductDetailsComponent },
  { path: 'signin', component: LoginComponent },
  { path: 'signup', component: RegisterComponent },
  { path: 'services', component:ServicesComponent},
  { path: 'sous-categorie', component: SousCategoryCreateComponent },
  {path: 'team', component: TeamComponent},
  { path: 'track-order', component: TrackOrderComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}
