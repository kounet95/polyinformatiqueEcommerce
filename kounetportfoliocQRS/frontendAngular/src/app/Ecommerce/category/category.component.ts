import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service'; 
import { ProductService } from '../services/produit.service';
import { CategoryDTO, ProductDTO } from '../../mesModels/models';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
  standalone: false
})
export class CategoryComponent implements OnInit {
  categories: CategoryDTO[] = [];
  products: ProductDTO[] = [];
  loading: boolean = true;
  error: string | null = null;
  showMobileSearch: boolean = false;
  mobileSearch: string = '';

  // Filtres
  selectedCategoryId: string | null = null;
  selectedCouleurs: string[] = [];
  selectedSocialGroup: string | null = null;
  selectedProductSize: string | null = null;
  currentIndex: number = 0;
  intervalId: any;

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.fetchCategories();
  }

  fetchCategories(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (data) => {
        this.categories = data.map(cat => ({
          ...cat,
          children: [
            { id: '1', name: "Men's Wear" },
            { id: '2', name: "Women's Wear" },
            { id: '3', name: "Kids' Clothing" },
            { id: '4', name: "Accessories" }
          ]
        }));
        this.loading = false;
        // Charge les produits de la première catégorie par défaut
        if (this.categories.length > 0) {
          this.onCategorySelect(this.categories[0].id);
        }
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
    ).subscribe({
      next: (data) => {
        this.products = data.content;
        this.loading = false;
      },
      error: () => {
        this.error = "Erreur lors du chargement des produits.";
        this.loading = false;
      }
    });
  }

  // Méthode appelée depuis le template, évite le cast dans le HTML
  onCouleurCheckboxChange(couleur: string, event: Event): void {
    const checked = (event.target as HTMLInputElement).checked;
    this.onCouleurChange(couleur, checked);
  }

  // Logique de gestion du tableau de couleurs sélectionnées
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

  onMobileSearch(): void {
    // Ajoute ici ta logique de recherche par texte si tu veux
    console.log(this.mobileSearch);
  }

  toggleMobileSearch(): void {
    this.showMobileSearch = !this.showMobileSearch;
  }
}