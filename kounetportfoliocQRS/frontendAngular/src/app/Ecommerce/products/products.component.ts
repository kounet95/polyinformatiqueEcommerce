import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/produit.service';
import { ProductDTO } from '../../mesModels/models';
import { PageResponse } from '../../mesModels/page-response.model';

@Component({
  selector: 'app-product',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
  standalone: false
})
export class ProductComponent implements OnInit {
  products: ProductDTO[] = [];
  loading: boolean = true;
  error: string | null = null;
  page: number = 0;
  size: number = 10;
  totalElements: number = 0;
  showMobileSearch: boolean = false;
  mobileSearch: string = '';

  // Exemple d'annonces pour la barre d'annonce
  announcements: string[] = [
    "🚚 Free shipping on orders over $50",
    "💰 30 days money back guarantee",
    "🎁 20% off on your first order - Use code: FIRST20",
    "⚡ Flash Sale! Up to 70% off on selected items"
  ];
  currentIndex: number = 0;
  intervalId: any;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.fetchProducts();
    // (Optionnel) Tu peux initialiser ici la rotation automatique des annonces si besoin
  }

  fetchProducts(): void {
    this.loading = true;
    this.productService.getAllProducts(this.page, this.size).subscribe({
      next: (data: PageResponse<ProductDTO>) => {
        this.products = data.content;
        this.totalElements = data.totalElements;
        this.loading = false;
      },
      error: () => {
        this.error = "Erreur lors du chargement des produits.";
        this.loading = false;
      }
    });
  }

  onMobileSearch(): void {
    // Ajoute ici ta logique de recherche sur les produits
    console.log(this.mobileSearch);
  }

  toggleMobileSearch(): void {
    this.showMobileSearch = !this.showMobileSearch;
  }

  // Exemples pour la pagination
  nextPage(): void {
    if ((this.page + 1) * this.size < this.totalElements) {
      this.page++;
      this.fetchProducts();
    }
  }
  prevPage(): void {
    if (this.page > 0) {
      this.page--;
      this.fetchProducts();
    }
  }
}