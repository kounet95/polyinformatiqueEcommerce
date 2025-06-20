import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service'; 
import { ProductService } from '../services/produit.service';
import { CategoryDTO, ProductDTO, ProductSizeDTO, SubcategoryDTO } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';
import { SouscategoriesService } from '../services/souscategories.service';
import { CartItem, CartService } from '../services/cartservice';

type CategoryWithChildren = CategoryDTO & { children?: { id: string; name: string }[] };
type ProductWithSelection = ProductDTO & { selectedSizeId?: string };
@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
  standalone: false
})
export class CategoryComponent implements OnInit {
  categories: CategoryWithChildren[] = [];
  products: ProductWithSelection[] = [];
  loading: boolean = true;
  error: string | null = null;
  showMobileSearch: boolean = false;
  mobileSearch: string = '';
  productSizes: ProductSizeDTO[] = [];
  // Filtres
  selectedCategoryId: string | null = null;
  selectedCouleurs: string[] = [];
  selectedSouscategorie: string[] = [];
  selectedSocialGroup: string | null = null;
  selectedProductSize: string | null = null;

  // Nouveaux filtres visuels
  searchKeyword: string = '';
  selectedPriceRange: string = '';
  sortOption: string = 'featured';
  viewMode: 'grid' | 'list' = 'grid';
  itemsPerPage: number = 12;
  activeFilters: { key: string, label: string }[] = [];

  currentIndex: number = 0;
  intervalId: any;

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private productSizeService: ProductSizeService,
    private sousCategorieService: SouscategoriesService,
   private cartService: CartService ,
   
  ) {}

  ngOnInit(): void {
    this.fetchCategoriesAndSubcategories();
  }

  fetchCategoriesAndSubcategories(): void {
    this.loading = true;
    this.categoryService.getAllCategories().subscribe({
      next: (cats) => {
        this.sousCategorieService.getAllSubcategories().subscribe({
          next: (sousCats) => {
            this.categories = cats.map(cat => ({
              ...cat,
              children: sousCats
                .filter(sc => sc.categoryId === cat.id)
                .map(sc => ({ id: sc.id, name: sc.name }))
            }));
            this.loading = false;
            if (this.categories.length > 0) {
              this.onCategorySelect(this.categories[0].id);
            }
          },
          error: () => {
            this.error = "Erreur lors du chargement des sous-catégories.";
            this.loading = false;
          }
        });
      },
      error: () => {
        this.error = "Erreur lors du chargement des catégories.";
        this.loading = false;
      }
    });
  }

  onCategorySelect(categoryId: string): void {
    this.selectedCategoryId = categoryId;
    this.applyFilters();
  }

  fetchProducts(): void {
    this.loading = true;
    this.products = [];

    this.productService.searchProducts(
      0, // page
      this.itemsPerPage,
      this.selectedCategoryId || undefined,
      this.selectedCouleurs.length > 0 ? this.selectedCouleurs.join(',') : undefined,
      this.selectedSocialGroup || undefined,
      this.selectedProductSize || undefined,
      this.selectedSouscategorie.length > 0 ? this.selectedSouscategorie.join(',') : undefined,
      this.searchKeyword || undefined,
      this.selectedPriceRange || undefined,
      this.sortOption || undefined
    ).subscribe({
      next: (data) => {
        const arr = Array.isArray(data) ? data : data.content ?? [];
        this.products = arr.map(prod => ({
  ...prod,
  productSizes: prod.productSizes ?? [],
  selectedSizeId: prod.productSizes?.[0]?.id
}));
        this.loading = false;
      },
      error: () => {
        this.error = "Erreur lors du chargement des produits.";
        this.loading = false;
      }
    });
  }

  applyFilters(): void {
    this.activeFilters = [];

    if (this.searchKeyword) {
      this.activeFilters.push({ key: 'search', label: this.searchKeyword });
    }

    if (this.selectedPriceRange) {
      this.activeFilters.push({
        key: 'price',
        label: this.selectedPriceRange.replace('-', ' to ').replace('+', '+')
      });
    }

    if (this.selectedCategoryId) {
      const cat = this.categories.find(c => c.id === this.selectedCategoryId);
      if (cat) {
        this.activeFilters.push({ key: 'category', label: cat.name });
      }
    }

    this.fetchProducts();
  }

  removeFilter(key: string): void {
    if (key === 'search') this.searchKeyword = '';
    if (key === 'price') this.selectedPriceRange = '';
    if (key === 'category') this.selectedCategoryId = null;
    this.applyFilters();
  }

  clearAllFilters(): void {
    this.searchKeyword = '';
    this.selectedPriceRange = '';
    this.sortOption = 'featured';
    this.itemsPerPage = 12;
    this.selectedCategoryId = null;
    this.activeFilters = [];
    this.fetchProducts();
  }

  clearAllColors(): void {
    this.selectedCouleurs = [];
    this.fetchProducts();
  }

  applyColorFilters(): void {
    this.fetchProducts();
  }

  onCouleurCheckboxChange(couleur: string, event: Event): void {
    const checked = (event.target as HTMLInputElement).checked;
    this.onCouleurChange(couleur, checked);
  }

  onCouleurChange(couleur: string, checked: boolean): void {
    if (checked && !this.selectedCouleurs.includes(couleur)) {
      this.selectedCouleurs.push(couleur);
    } else if (!checked) {
      this.selectedCouleurs = this.selectedCouleurs.filter(c => c !== couleur);
    }
    this.fetchProducts();
  }

   addToCart(product: ProductDTO, qty: number = 1, productSizeId?: string, productSize?: string): void {
    this.cartService.addToCart(product, qty, productSizeId, productSize);
  }
  
  
 getSelectedSizeName(product: ProductWithSelection): string | undefined {
    if (!product.productSizes) return undefined;
    const sz = product.productSizes.find(s => s.id === product.selectedSizeId) ||
               product.productSizes[0];
    return sz?.size;
  }
   
  onSocialGroupChange(sgId: string): void {
    this.selectedSocialGroup = sgId;
    this.fetchProducts();
  }

  onProductSizeChange(size: string): void {
    this.selectedProductSize = size;
    this.fetchProducts();
  }

  onProductSouscategorieChange(id: string): void {
    this.selectedSouscategorie = this.selectedSouscategorie.filter(c => c !== id); 
    this.fetchProducts();
  }

  onMobileSearch(): void {
    this.searchKeyword = this.mobileSearch;
    this.applyFilters();
  }

  toggleMobileSearch(): void {
    this.showMobileSearch = !this.showMobileSearch;
  }

  getProductImageUrl(models: string): string {
    return models ?? 'assets/img/placeholder.png';
  }

  getMainProductPrice(product: ProductDTO): number | null {
    return product.productSizes?.[0]?.price ?? null;
  }
}
