import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service'; 
import { ProductService } from '../services/produit.service';
import { CategoryDTO, ProductDTO, ProductSizeDTO } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';
import { SouscategoriesService } from '../services/souscategories.service';
import { CartService } from '../services/cartservice';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

type CategoryWithChildren = CategoryDTO & { children?: { id: string; name: string }[] };

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
  standalone: false
})
export class CategoryComponent implements OnInit {
  categories: CategoryWithChildren[] = [];
  //pour afficher des produits, conserve products, sinon mets uniquement productSizes
  products: ProductDTO[] = [];
  displayedColumns = ["id", "sizeProd", "price", "pricePromo"];
  loading: boolean = true;
  error: string | null = null;
  showMobileSearch: boolean = false;
  mobileSearch: string = '';
  productSizes: ProductSizeDTO[] = [];
  datasource: any;

  filters = {
    selectedCategoryId: null as string | null,
    selectedCouleurs: [] as string[],
    selectedSouscategorie: [] as string[],
    selectedSocialGroup: null as string | null,
    selectedProductSize: null as string | null,
    searchKeyword: '',
    selectedPriceRange: '',
    sortOption: 'featured',
    viewMode: 'grid' as 'grid' | 'list',
    itemsPerPage: 12
  };

  activeFilters: { key: string, label: string }[] = [];

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private productSizeService: ProductSizeService,
    private sousCategorieService: SouscategoriesService,
    private cartService: CartService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchCategoriesAndSubcategories();
    this.fetchFilteredProductSizes();
    this.datasource = new MatTableDataSource();
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

  fetchFilteredProductSizes(): void {
    this.loading = true;
    this.productSizeService.searchProductSizes(
      this.filters.searchKeyword,
      undefined,
      undefined, 
      this.filters.selectedProductSize?? undefined,
      undefined, 
      undefined, 
      this.filters.selectedSouscategorie.length > 0 ? this.filters.selectedSouscategorie[0] : undefined,
      this.filters.selectedSocialGroup?? undefined,
    ).subscribe({
      next: (results: ProductSizeDTO[]) => {
        this.productSizes = results;
        this.datasource = new MatTableDataSource(this.productSizes);
        this.loading = false;
      },
      error: () => {
        this.error = "Erreur lors de la recherche avancée.";
        this.loading = false;
      }
    });
  }

  onCategorySelect(categoryId: string): void {
    this.filters.selectedCategoryId = categoryId;
    
     this.filters.selectedSouscategorie = this.categories.find(c => c.id === categoryId)?.children?.map(child => child.id) || [];
    this.applyFilters();
  }

  applyFilters(): void {
    this.activeFilters = [];

    if (this.filters.searchKeyword) {
      this.activeFilters.push({ key: 'search', label: this.filters.searchKeyword });
    }
    if (this.filters.selectedPriceRange) {
      this.activeFilters.push({
        key: 'price',
        label: this.filters.selectedPriceRange.replace('-', ' to ').replace('+', '+')
      });
    }
    if (this.filters.selectedCategoryId) {
      const cat = this.categories.find(c => c.id === this.filters.selectedCategoryId);
      if (cat) {
        this.activeFilters.push({ key: 'category', label: cat.name });
      }
    }
    // Ajoute d'autres filtres si besoin (couleur, sous-catégorie, etc.)
    this.fetchFilteredProductSizes();
  }

  removeFilter(key: string): void {
    if (key === 'search') this.filters.searchKeyword = '';
    if (key === 'price') this.filters.selectedPriceRange = '';
    if (key === 'category') this.filters.selectedCategoryId = null;
    // Réinitialise d'autres filtres si besoin
    this.applyFilters();
  }

  clearAllFilters(): void {
    this.filters.searchKeyword = '';
    this.filters.selectedPriceRange = '';
    this.filters.sortOption = 'featured';
    this.filters.itemsPerPage = 12;
    this.filters.selectedCategoryId = null;
    this.filters.selectedSouscategorie = [];
    this.filters.selectedSocialGroup = null;
    this.filters.selectedProductSize = null;
    this.activeFilters = [];
    this.fetchFilteredProductSizes();
  }

  clearAllColors(): void {
    this.filters.selectedCouleurs = [];
    this.fetchFilteredProductSizes();
  }

  applyColorFilters(): void {
    this.fetchFilteredProductSizes();
  }

  onCouleurCheckboxChange(couleur: string, event: Event): void {
    const checked = (event.target as HTMLInputElement).checked;
    this.onCouleurChange(couleur, checked);
  }

  onCouleurChange(couleur: string, checked: boolean): void {
    if (checked && !this.filters.selectedCouleurs.includes(couleur)) {
      this.filters.selectedCouleurs.push(couleur);
    } else if (!checked) {
      this.filters.selectedCouleurs = this.filters.selectedCouleurs.filter(c => c !== couleur);
    }
    this.fetchFilteredProductSizes();
  }

  onSocialGroupChange(sgId: string): void {
    this.filters.selectedSocialGroup = sgId;
    this.fetchFilteredProductSizes();
  }

  onProductSizeChange(size: string): void {
    this.filters.selectedProductSize = size;
    this.fetchFilteredProductSizes();
  }

  onProductSouscategorieChange(id: string): void {
    // Si tu veux gérer plusieurs sous-catégories
    if (this.filters.selectedSouscategorie.includes(id)) {
      this.filters.selectedSouscategorie = this.filters.selectedSouscategorie.filter(c => c !== id);
    } else {
      this.filters.selectedSouscategorie.push(id);
    }
    this.fetchFilteredProductSizes();
  }

  onMobileSearch(): void {
    this.filters.searchKeyword = this.mobileSearch;
    this.applyFilters();
  }

  toggleMobileSearch(): void {
    this.showMobileSearch = !this.showMobileSearch;
  }

  getProductImageUrl(models: string): string {
    return models ?? 'assets/img/placeholder.png';
  }

  addCart(size: ProductSizeDTO) {
    this.cartService.addToCart(size, 1);
    this.snackBar.open(
      `Ajouté: ${size.prodId.name} - ${size.sizeProd} au panier !`,
      'Fermer',
      { duration: 2000, verticalPosition: 'top' }
    );
  }
  voirDetaille(id: string){
  this.router.navigate([`/product/${id}`]);
  }
  get allFilters(): Record<string, any> {
    return { ...this.filters };
  }
}