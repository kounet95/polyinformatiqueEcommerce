import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service'; 
import { ProductService } from '../services/produit.service';
import { CategoryDTO, ProductDTO, SubcategoryDTO } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';
import { SouscategoriesService } from '../services/souscategories.service';

type CategoryWithChildren = CategoryDTO & { children?: { id: string; name: string }[] };

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
  standalone: false
})
export class CategoryComponent implements OnInit {
  categories: CategoryWithChildren[] = [];
  products: ProductDTO[] = [];
  loading: boolean = true;
  error: string | null = null;
  showMobileSearch: boolean = false;
  mobileSearch: string = '';
 
  // Filtres
  selectedCategoryId: string | null = null;
  selectedCouleurs: string[] = [];
  selectedSouscategorie: string[] = [];
  selectedSocialGroup: string | null = null;
  selectedProductSize: string | null = null;
  currentIndex: number = 0;
  intervalId: any;

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private productSizeService: ProductSizeService,
    private sousCategorieService: SouscategoriesService
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
    this.fetchProducts();
  }

  fetchProducts(): void {
    this.loading = true;
    this.products = [];
    this.productService.searchProducts(
      0, // page
      12, // size
      this.selectedCategoryId || undefined,
      this.selectedCouleurs.length > 0 ? this.selectedCouleurs.join(',') : undefined,
      this.selectedSocialGroup || undefined,
      this.selectedProductSize || undefined,
      this.selectedSouscategorie.length > 0 ? this.selectedSouscategorie.join(',') : undefined,
    ).subscribe({
      next: (data) => {
        const arr = Array.isArray(data) ? data : data.content ?? [];
      this.products = arr.map(prod => ({
        ...prod,
        productSizes: prod.productSizes ?? []
      }));
        this.loading = false;
      },
      error: () => {
        this.error = "Erreur lors du chargement des produits.";
        this.loading = false;
      }
    });
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
    // Ajoute ici ta logique de recherche par texte si tu veux
    console.log(this.mobileSearch);
  }

  toggleMobileSearch(): void {
    this.showMobileSearch = !this.showMobileSearch;
  }

getProductImageUrl(models: string): string {
  return models ?? 'assets/img/placeholder.png';
}




  /**
   * Retourne le prix principal d'un produit (prix de la première taille).
   * Si aucune taille, retourne null.
   */
  getMainPrice(product: ProductDTO): number | null {
    if (product.productSizes && product.productSizes.length > 0) {
      return product.productSizes[0].price;
    }
    return null;
  }
}