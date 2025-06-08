import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../services/category.service'; // adapte le chemin si besoin
import { CategoryDTO, SubcategoryDTO } from '../../mesModels/models'; // adapte le chemin si besoin
import { CategoryCreateComponent } from '../category-create/category-create.component';

@Component({
  selector: 'app-category-create',
  standalone: false,
  templateUrl: './sous-category-create.component.html',
  styleUrls: ['./sous-category-create.component.css']
})
export class SousCategoryCreateComponent implements OnInit {
  categoryForm: FormGroup;
  categories: CategoryDTO[] = [];
  successMessage?: string;
  errorMessage?: string;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService
  ) {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      categoryId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getAllCategories().subscribe({
      next: cats => this.categories = cats,
      error: () => this.errorMessage = "Impossible de charger les catégories."
    });
  }

  onSubmit() {
    this.successMessage = undefined;
    this.errorMessage = undefined;

    if (this.categoryForm.invalid) {
      this.categoryForm.markAllAsTouched();
      return;
    }

    const subcategory: SubcategoryDTO = {
      id: '', // Généré côté backend
      name: this.categoryForm.value.name,
      categoryId: this.categoryForm.value.categoryId
    };

    this.loading = true;
    this.categoryService.createCategory(subcategory).subscribe({
      next: () => {
        this.successMessage = 'Sous-catégorie créée avec succès !';
        this.loading = false;
        this.categoryForm.reset();
      },
      error: err => {
        this.errorMessage = "Erreur lors de la création de la sous-catégorie.";
        this.loading = false;
      }
    });
  }
}