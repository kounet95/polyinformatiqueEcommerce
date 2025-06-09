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
import { DetaillProductComponent } from './Ecommerce/detaill-product/detaill-product.component';
import { TrackOrderComponent } from './Ecommerce/track-order/track-order.component';
import { CategoryComponent } from './Ecommerce/category/category.component';
import { CategoryCreateComponent } from './Ecommerce/category-create/category-create.component';
import { CreateProductComponent } from './Ecommerce/create-product/create-product.component';
import { SousCategoryCreateComponent } from './Ecommerce/sous-category-create/sous-category-create.component';

const routes: Routes = [
  { path: '', loadComponent: () => import('./acceuil/acceuil.component').then(m => m.AcceuilComponent) },
  { path: 'about', component: AboutComponent },
 {
  path: 'services', component:ServicesComponent,
},
{
  path: 'team', component: TeamComponent,
},
  {
  path: 'portfolio',component: PortfolioComponent,
}
,
  { path: 'pricing', component: PrincingComponent },
  { path: 'blog', component: BlogComponent },
  { path: 'newsArticle', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'createDomain', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'createTag', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'createNews', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'createEvent', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'commentaire', component: NewsArticleComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },
  { path: 'contact', loadComponent: () => import('./contact/contact.component').then(m => m.ContactComponent) },
  { path: 'articles', component: ArticleComponent },
  { path: 'home', component: HomeComponent },
  { path: 'product', component: DetaillProductComponent },
  { path: 'souscategorie', component: SousCategoryCreateComponent },
  { path: 'track-order', component: TrackOrderComponent },
  { path: 'category', component: CategoryComponent },
  { path: 'product-details', component: DetaillProductComponent },
  { path: 'productcreate', component: CreateProductComponent },
  { path: 'categoryCreate', component: CategoryCreateComponent, canActivate: [AuthGuard], data: { roles: ['ADMIN'] } },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)], 
  exports: [RouterModule]
})

export class AppRoutingModule {}
