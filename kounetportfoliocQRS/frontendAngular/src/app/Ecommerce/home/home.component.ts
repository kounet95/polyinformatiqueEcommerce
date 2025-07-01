import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/produit.service';
import { CategoryService } from '../services/category.service';
import { ProductDTO, CategoryDTO, ProductSizeDTO } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone: false
})
export class HomeComponent implements OnInit {
  featuredProducts: ProductDTO[] = [];
  featuredProductsSize: ProductSizeDTO[] = [];
  newArrivals: ProductSizeDTO[] = [];
  saleProducts: ProductSizeDTO[] = [];
  categories: CategoryDTO[] = [];
  selectedLanguage = 'English';
  selectedCurrency = 'USD';

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private productSizeService: ProductSizeService
  ) {}

  ngOnInit() {
    this.loadFeaturedProducts();
    this.loadFeaturedProductsSize();
    this.loadNewArrivals();
    this.loadSaleProducts();
    this.loadCategories();
  }

  /** ✅ suppose que getAllProducts() retourne un Page<ProductDTO> */
  loadFeaturedProducts() {
    this.productService.getAllProducts(0, 10).subscribe({
      next: page => this.featuredProducts = page.content || [],
      error: () => this.featuredProducts = []
    });
  }

  /** ✅ utilise bien la pagination ProductSize */
  loadFeaturedProductsSize() {
    this.productSizeService.getAllProductSizes(0, 10).subscribe({
      next: page => this.featuredProductsSize = page.content || [],
      error: () => this.featuredProductsSize = []
    });
  }

  loadNewArrivals() {
    const date = new Date();
    this.productSizeService.getNewArrivals(date).subscribe({
      next: prods => this.newArrivals = prods || [],
      error: () => this.newArrivals = []
    });
  }

  loadSaleProducts() {
    this.productSizeService.getSaleProducts().subscribe({
      next: prods => this.saleProducts = prods || [],
      error: () => this.saleProducts = []
    });
  }

  loadCategories() {
    this.categoryService.getAllCategories().subscribe({
      next: cats => this.categories = cats || [],
      error: () => this.categories = []
    });
  }

  selectLanguage(lang: string) {
    this.selectedLanguage = lang;
  }

  selectCurrency(curr: string) {
    this.selectedCurrency = curr;
  }
}
