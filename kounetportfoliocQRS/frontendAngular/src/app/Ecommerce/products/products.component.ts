import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/produit.service';
import { ProductDTO } from '../../mesModels/models';
import { PageResponse } from '../../mesModels/page-response.model';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddressFormComponent } from '../address-form/address-form.component';

@Component({
  selector: 'app-product',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
 standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AddressFormComponent,
    FormsModule,
  ]
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
    // (Facultatif) Lancer la rotation des annonces automatiquement
    this.startAnnouncementRotation();
  }

 fetchProducts(): void {
  this.loading = true;
  this.error = null;

  this.productService.getAllProducts(this.page, this.size).subscribe({
    next: (data: any) => {
      // Si data est un tableau simple :
      if (Array.isArray(data)) {
        this.products = data;
        this.totalElements = data.length;
      } else {
        // Sinon on suppose que data a content et totalElements
        this.products = data.content ?? [];
        this.totalElements = data.totalElements ?? 0;
      }
      this.loading = false;
    },
    error: () => {
      this.error = 'Erreur lors du chargement des produits.';
      this.loading = false;
    }
  });
}

get totalPages(): number {
  return Math.ceil(this.totalElements / this.size) || 1;
}

  onMobileSearch(): void {
    console.log('Recherche mobile : ', this.mobileSearch);
    // Implémente ta logique ici si tu veux filtrer côté front
  }

  toggleMobileSearch(): void {
    this.showMobileSearch = !this.showMobileSearch;
  }

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

  startAnnouncementRotation(): void {
    this.intervalId = setInterval(() => {
      this.currentIndex = (this.currentIndex + 1) % this.announcements.length;
    }, 5000); // Change toutes les 5s
  }

  stopAnnouncementRotation(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  ngOnDestroy(): void {
    this.stopAnnouncementRotation();
  }
}
