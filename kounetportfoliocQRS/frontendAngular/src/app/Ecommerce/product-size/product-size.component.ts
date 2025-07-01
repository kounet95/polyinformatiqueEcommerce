import { Component } from '@angular/core';
import { ProductSizeService } from '../services/product-size.service';
import { ProductSizeDTO } from '../../mesModels/models';
@Component({
  selector: 'app-product-size',
  standalone: false,
  templateUrl: './product-size.component.html',
  styleUrl: './product-size.component.css'
})
export class ProductSizeComponent {

 productSizes: ProductSizeDTO[] = [];
    loading: boolean = true;
  error: string | null = null;

  page: number = 0;
  size: number = 10;
  totalElements: number = 0;
announcements: string[] = [
    "🚚 Free shipping on orders over $50",
    "💰 30 days money back guarantee",
    "🎁 20% off on your first order - Use code: FIRST20",
    "⚡ Flash Sale! Up to 70% off on selected items"
  ];
  currentIndex: number = 0;
  intervalId: any;

  constructor(private productSizeService: ProductSizeService) {}

  ngOnInit(): void {
    this.loadProductSizes();
  }

  loadProductSizes(): void {
     this.loading = true;
   this.productSizeService.getAllProductsise(
    this.page, this.size).subscribe({
    next: (result: any) => {
      if (Array.isArray(result)) {
        this.productSizes = result;
        this.totalElements = result.length;
      } else {
        this.productSizes = result.content ?? [];
        this.totalElements = result.totalElements ?? 0;
      
      }
      this.loading = false;
    },
    error: () => {
      this.error = 
      'Erreur lors du chargement des tailles de produit.';
      this.loading = false;
    }
  });
}
  get totalPages(): number {
  return Math.ceil(this.totalElements / this.size) || 1;
}

  nextPage(): void {
    if ((this.page + 1) * this.size < this.totalElements) {
      this.page++;
      this.loadProductSizes();
    }
  }

  prevPage(): void {
     if (this.page > 0) {
      this.page--;
      this.loadProductSizes();
    }
  }
}

