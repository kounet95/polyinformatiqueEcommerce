import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service'; 
import { CategoryDTO } from '../../mesModels/models';

@Component({
  selector: 'app-category',
  standalone:false,
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  categories: CategoryDTO[] = [];
  loading = true;
  mobileSearch: string = '';
  error: string | null = null;
  showMobileSearch = false;
    announcements = [
    "🚚 Free shipping on orders over $50",
    "💰 30 days money back guarantee",
    "🎁 20% off on your first order - Use code: FIRST20",
    "⚡ Flash Sale! Up to 70% off on selected items"
  ];
  currentIndex = 0;
  intervalId: any;

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (data) => {
        // Pour la démo/POC, chaque catégorie a des sous-catégories factices
        this.categories = data.map((cat: any) => ({
          id: cat.id,
          name: cat.name,
          description: cat.description,
          imageUrl: cat.imageUrl,
          parentId: cat.parentId,
          children: [
            { id: '1', name: "Men's Wear" },
            { id: '2', name: "Women's Wear" },
            { id: '3', name: "Kids' Clothing" },
            { id: '4', name: "Accessories" }
          ]
        }));
        this.loading = false;
      },
      error: () => {
        this.error = "Erreur lors du chargement des catégories.";
        this.loading = false;
      }
    });
  }

  onMobileSearch() {
    // Ajoute ici ta logique de recherche
    console.log(this.mobileSearch);
  }

  toggleMobileSearch() {
    this.showMobileSearch = !this.showMobileSearch;
  }
}