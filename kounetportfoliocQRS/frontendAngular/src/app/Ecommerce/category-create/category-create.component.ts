import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../services/category.service'; // adapte le chemin si besoin
import { CategoryDTO } from '../../mesModels/models'; // adapte le chemin si besoin

@Component({
  selector: 'app-category-create',
  standalone: false,
  templateUrl: './category-create.component.html',
  styleUrls: ['./category-create.component.css']
})
export class CategoryCreateComponent {
  categoryForm: FormGroup;
  successMessage?: string;
  errorMessage?: string;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService
  ) {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]]
    });
  }

  onSubmit() {
    this.successMessage = undefined;
    this.errorMessage = undefined;

    if (this.categoryForm.invalid) {
      this.categoryForm.markAllAsTouched();
      return;
    }

    const category: CategoryDTO = {
      id: '', // laissé vide, généré côté backend normalement
      name: this.categoryForm.value.name
    };

    this.loading = true;
    this.categoryService.createCategory(category).subscribe({
      next: () => {
        this.successMessage = 'Catégorie créée avec succès !';
        this.loading = false;
        this.categoryForm.reset();
      },
      error: err => {
        this.errorMessage = "Erreur lors de la création de la catégorie.";
        this.loading = false;
      }
    });
  }
}