import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/produit.service';
import { CategoryService } from '../services/category.service';
import { ProductDTO, CategoryDTO, SubcategoryDTO } from '../../mesModels/models';
import { StockService } from '../services/stock.service';
import { CommonModule } from '@angular/common';
import { SouscategoriesService } from '../services/souscategories.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true,
  imports: [ CommonModule ]
})
export class HomeComponent implements OnInit {
  featuredProducts: ProductDTO[] = [];
  newArrivals: ProductDTO[] = [];
  saleProducts: ProductDTO[] = [];
  categories: CategoryDTO[] = [];
  sousCategories: SubcategoryDTO[] = [];
selectedCategory: CategoryDTO | null = null;
selectedSubcategories: SubcategoryDTO[] = [];
selectedProducts: ProductDTO[] = [];
  selectedLanguage = 'English';
  selectedCurrency = 'USD';

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private stockeService: StockService,
    private sousCategoryService: SouscategoriesService 
  ) {}

  ngOnInit() {
    this.loadFeaturedProducts();
    this.loadNewArrivals();
    this.loadSaleProducts();

  }

  /** Charge les produits vedettes */
  loadFeaturedProducts() {
    this.productService.getAllProducts(0, 10).subscribe({
      next: page => this.featuredProducts = page.content || [],
      error: () => this.featuredProducts = []
    });
  }

  

  

  /** Charge les nouveautés */
  loadNewArrivals() {
    this.productService.getAllProducts(0, 10).subscribe({
      next: page => this.newArrivals = page.content || [],
      error: () => this.newArrivals = []
    });
  }

  /** Charge les promos */
  loadSaleProducts() {
    this.productService.getAllProducts(0, 10).subscribe({
      next: page => this.saleProducts = page.content || [],
      error: () => this.saleProducts = []
    });
  }

 loadCategories() {
  this.categoryService.getAllCategories().subscribe({
    next: cats => this.categories = cats || [],
    error: () => console.error('Erreur chargement catégories')
  });
}

onSelectCategory(category: CategoryDTO) {
  this.selectedCategory = category;

  // Récupère les sous-catégories de la catégorie
  this.sousCategoryService.getSousCategoriesByCategoryId(category.id).subscribe({
    next: sousCats => {
      this.selectedSubcategories = sousCats || [] ;

      // Ensuite récupère tous les produits pour ces sous-catégories
    this.productService.getAllProducts().subscribe({
  next: page => {
    const products = page.content || [];
    this.selectedProducts = products.filter(prod =>
      this.selectedSubcategories.some(sous => sous.id === prod.subcategoryId)
    );
  },
  error: () => console.error('Erreur chargement produits')
});
    },
    error: () => console.error('Erreur chargement sous-catégories')
  });
}




  selectLanguage(lang: string) {
    this.selectedLanguage = lang;
  }

  selectCurrency(curr: string) {
    this.selectedCurrency = curr;
  }
}
