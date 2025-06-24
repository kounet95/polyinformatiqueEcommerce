import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/produit.service';
import { CategoryService } from '../services/category.service';
import { ProductDTO, CategoryDTO, ProductSizeDTO } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  standalone:false,
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

  // Suppose que getAllProducts() retourne un objet paginé avec une propriété 'content'
  loadFeaturedProducts() {
    this.productService.getAllProducts().subscribe({
      next: page => this.featuredProducts = page.content || [],
      error: err => this.featuredProducts = []
    });
  }

  loadFeaturedProductsSize() {
    this.productSizeService.getAllProductSizes().subscribe({
      next: prods => this.featuredProductsSize = prods || [],
      error: err => this.featuredProductsSize = []
    });
  }

  loadNewArrivals() {
    const date = new Date();
    this.productSizeService.getNewArrivals(date).subscribe({
      next: prods => this.newArrivals = prods || [],
      error: err => this.newArrivals = []
    });
  }

  loadSaleProducts() {
    this.productSizeService.getSaleProducts().subscribe({
      next: prods => this.saleProducts = prods || [],
      error: err => this.saleProducts = []
    });
  }

  loadCategories() {
    this.categoryService.getAllCategories().subscribe({
      next: cats => this.categories = cats || [],
      error: err => this.categories = []
    });
  }

  selectLanguage(lang: string) {
    this.selectedLanguage = lang;
  }

  selectCurrency(curr: string) {
    this.selectedCurrency = curr;
  }
}