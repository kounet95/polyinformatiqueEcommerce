import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/produit.service';
import { CategoryService } from '../services/category.service';
import { ProductDTO, CategoryDTO, ProductSizeDTO, SubcategoryDTO } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';
import { StockService } from '../services/stock.service';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { SouscategoriesService } from '../services/souscategories.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: true,
    imports: [
    CommonModule ]
})
export class HomeComponent implements OnInit {
  featuredProducts: ProductDTO[] = [];
  featuredProductsSize: ProductSizeDTO[] = [];
  newArrivals: ProductSizeDTO[] = [];
  saleProducts: ProductSizeDTO[] = [];
  categories: CategoryDTO[] = [];
  sousCategories: SubcategoryDTO[] = [];
  selectedLanguage = 'English';
  selectedCurrency = 'USD';

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private productSizeService: ProductSizeService,
    private stockeService: StockService,
    private sousCategoryService: SouscategoriesService
  ) {}

  ngOnInit() {
    this.loadFeaturedProducts();
    this.loadFeaturedProductsSize();
    this.loadNewArrivals();
    this.loadSaleProducts();
    
  }

  /** suppose que getAllProducts() retourne un Page<ProductDTO> */
  loadFeaturedProducts() {
    this.productService.getAllProducts(0, 10).subscribe({
      next: page => this.featuredProducts = page.content || [],
      error: () => this.featuredProducts = []
    });
  }

  /** utilise bien la pagination ProductSize */
  loadFeaturedProductsSize() {
    this.productSizeService.getAllProductSizes(0, 10).subscribe({
  next: page => {
    const sizes = page.content || [];

    // Suppose que tu as déjà tous les produits :
    this.productService.getAllProducts().subscribe({
      next: productPage => {
        const products = productPage.content || [];

        this.featuredProductsSize = sizes.map(size => {
          const matched = products.find(p => p.id === size.prodId);
          return {
            ...size,
            product: matched ?? {
              id: 'unknown',
              name: 'Default Product',
              models: '',
              description: '',
              createdAt: '',
              subcategoryId: '',
              socialGroupId: '',
              isActive: true
            }
          };
        });
      }
    });
  }
});
  }

  

 loadNewArrivals() {
  const date = new Date();
  this.stockeService.getNewArrivals(date).subscribe({
    next: (sizes: ProductSizeDTO[]) => {
      this.productService.getAllProducts(0, 100).subscribe({
        next: (page) => {
          const products = page.content || [];
          this.newArrivals = sizes.map(size => {
            const matchedProduct = products.find(p => p.id === size.prodId);
            return {
              ...size,
              product: matchedProduct ?? {
                id: size.prodId || 'unknown',
                name: 'Produit inconnu',
                description: '',
                createdAt: '',
                models: size.frontUrl || '',
                subcategoryId: '',
                socialGroupId: '',
                isActive: false
              }
            };
          });
        },
        error: () => {
          console.error('Erreur chargement produits');
          this.newArrivals = [];
        }
      });
    },
    error: () => {
      console.error('Erreur chargement stocks');
      this.newArrivals = [];
    }
  });
}

 loadSaleProducts() {
  this.stockeService.getOnSale().subscribe({
    next: (sizes: ProductSizeDTO[]) => {
      this.productService.getAllProducts(0, 100).subscribe({
        next: (page) => {
          const products = page.content || [];
          this.saleProducts = sizes.map(size => {
            const matchedProduct = products.find(p => p.id === size.prodId);
            return {
              ...size,
              product: matchedProduct ?? {
                id: size.prodId || 'unknown',
                name: 'Produit inconnu',
                description: '',
                createdAt: '',
                models: size.frontUrl || '',
                subcategoryId: '',
                socialGroupId: '',
                isActive: false
              }
            };
          });
        }
      });
    }
  });
}


  loadCategoriesAndSousCategories() {
  this.categoryService.getAllCategories().subscribe({
    next: categories => {
      this.sousCategoryService.getAllSubcategories().subscribe({
        next: sousCats => {
          // On imbrique
          this.categories = categories.map(cat => ({
            ...cat,
            sousCategories: sousCats.filter(sous => sous.categoryId === cat.id)
          }));

          console.log('Catégories imbriquées :', this.categories);

          // Tu peux ensuite charger les produits pour chaque sous-catégorie ici :
          this.categories.forEach(cat => {
            cat.sousCategories.forEach(sous => {
              this.productService.getProductsBySousCategoryId(sous.id).subscribe({
                next: prods => sous.products = prods || [],
                error: () => sous.products = []
              });
            });
          });
        },
        error: () => console.error('Erreur chargement sous-catégories')
      });
    },
    error: () => console.error('Erreur chargement catégories')
  });
}

  selectLanguage(lang: string) {
    this.selectedLanguage = lang;
  }

  selectCurrency(curr: string) {
    this.selectedCurrency = curr;
  }

  
}
