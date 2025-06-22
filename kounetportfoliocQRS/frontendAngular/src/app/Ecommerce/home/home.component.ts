import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/produit.service';
import { CategoryService } from '../services/category.service';
import { ProductDTO, CategoryDTO, ProductSizeDTO } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  featuredProducts: ProductDTO[] = [];
  featuredProductsSize:ProductSizeDTO[]= [];
  newArrivals: ProductDTO[] = [];
  saleProducts: ProductDTO[] = [];
  categories: CategoryDTO[] = [];
  selectedLanguage = 'English';
  selectedCurrency = 'USD';

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private productSizeService: ProductSizeService
  ) {}

  ngOnInit() {
    // Appelle tes services pour charger les produits et catégories dynamiquement
    this.loadFeaturedProducts();
    this.loadFeaturedProductsSize();
    this.loadNewArrivals();
    this.loadSaleProducts();
    this.loadCategories();
  }

  loadFeaturedProducts() {
    this.productService.getAllProducts().subscribe(prods => this.featuredProducts = prods);
  }

  loadFeaturedProductsSize() {
    this.productService.getAllProducts().subscribe(
      prods => this.featuredProductsSize = prods);
  }

  loadNewArrivals() {
    this.productService.getNewArrivals().subscribe(prods => this.newArrivals = prods);
  }

  loadSaleProducts() {
    this.productService.getSaleProducts().subscribe(prods => this.saleProducts = prods);
  }

  loadCategories() {
    this.categoryService.getAllCategories().subscribe(cats => this.categories = cats);
  }

  selectLanguage(lang: string) { this.selectedLanguage = lang; }
  selectCurrency(curr: string) { this.selectedCurrency = curr; }
}