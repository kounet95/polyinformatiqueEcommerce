import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service'; 
import { ProductService } from '../services/produit.service';
import { CategoryDTO, ProductDTO, ProductSizeDTO, SubcategoryDTO,CartItem } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';
import { SouscategoriesService } from '../services/souscategories.service';
import { CartService } from '../services/cartservice';
import { MatTableDataSource } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';

type CategoryWithChildren = CategoryDTO & { children?: { id: string; name: string }[] };
type ProductWithSelection = ProductDTO & { 
  selectedSizeId?: string; 
  productSizes?: ProductSizeDTO[];
  sizes?:string;
};

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
  standalone: false
})
export class CategoryComponent implements OnInit {
  categories: CategoryWithChildren[] = [];
  products: ProductWithSelection[] = [];
  displayedColumns =["id" ,"sizeProd", "price", "pricePromo"];
  loading: boolean = true;
  error: string | null = null;
  showMobileSearch: boolean = false;
  mobileSearch: string = '';
  productSizes: ProductSizeDTO[] = [];
  public datasource:any;
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

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private productSizeService: ProductSizeService,
    private sousCategorieService: SouscategoriesService,
    private cartService: CartService,
    private snackBar: MatSnackBar
  ) {}

 ngOnInit(): void {
  this.fetchCategoriesAndSubcategories();
  this.fetchProductSizes();
  this.datasource=new MatTableDataSource();
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

  fetchProductSizes(): void {
    this.loading = true;
    this.productSizeService.getAllProductSizes().subscribe({
      next: (apiSizes: any[]) => {
        // Mapping API -> Front Model
        const sizes: ProductSizeDTO[] = apiSizes.map(apiPs => ({
          id: apiPs.id,
          sizeProd: apiPs.size, // API: size ; Front: sizeProd
          prodId: {
            id: apiPs.productId.id,
            name: apiPs.productId.name,
            description: apiPs.productId.description,
            createdAt: apiPs.productId.createdAt,
            models: apiPs.productId.urlModels,
            subcategoryId: apiPs.productId.subcategory?.id,
            socialGroupId: apiPs.productId.socialGroup?.id,
            isActive: apiPs.productId.active
          },
          price: apiPs.price,
          pricePromo: apiPs.promoPrice,
          imageUrl: apiPs.urlImage
        }));

        this.productSizes = sizes;

        // Pour une table à plat si besoin
        this.datasource = new MatTableDataSource(this.productSizes);
        this.loading = false;
      },
      error: () => {
        this.error = "Erreur lors du chargement des tailles/produits.";
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
          productSizes: [],
          selectedSizeId: undefined
        }));
        // Récupère les tailles pour chaque produit
        this.products.forEach(product => {
          this.productSizeService.getProductSizesByProductId(product.id).subscribe(sizes => {
            product.productSizes = sizes.map(apiPs => ({
              id: apiPs.id,
              sizeProd: apiPs.sizeProd,
              prodId: product,
              price: apiPs.price,
              pricePromo: apiPs.pricePromo,
              imageUrl: apiPs.imageUrl
            }));
            if (sizes.length > 0) {
              product.selectedSizeId = sizes[0].id;
            }
          });
        });
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

  getMainProductPrice(product: ProductSizeDTO): number | null {
    return null;
  }

addCart(size: ProductSizeDTO) {
    this.cartService.addToCart(size, 1);
    this.snackBar.open(
      `Ajouté: ${size.prodId.name} - ${size.sizeProd} au panier !`,
      'Fermer',
      { duration: 2000, verticalPosition: 'top' }
    );
  }

}