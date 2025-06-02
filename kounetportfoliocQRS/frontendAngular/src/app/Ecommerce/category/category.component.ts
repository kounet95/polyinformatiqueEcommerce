import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../services/category.service'; 
import { CategoryDTO } from '../../mesModels/models';

@Component({
  selector: 'app-category',
  standalone: false,
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  categories: CategoryDTO[] = [];
  selectedCategory?: CategoryDTO;
  errorMessage?: string;
  loading = false;

  constructor(private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.fetchCategories();
  }

  fetchCategories() {
    this.loading = true;
    this.categoryService.getAllCategories().subscribe({
      next: (data) => {
        this.categories = data;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = "Erreur lors de la récupération des catégories.";
        this.loading = false;
      }
    });
  }

  selectCategory(id: string) {
    this.selectedCategory = undefined;
    this.loading = true;
    this.categoryService.getCategoryById(id).subscribe({
      next: (data) => {
        this.selectedCategory = data;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = "Erreur lors de la récupération de la catégorie.";
        this.loading = false;
      }
    });
  }
}