import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../services/category.service'; // adapte le chemin si besoin
import { CategoryDTO } from '../../mesModels/models'; // adapte le chemin si besoin
import { Router } from '@angular/router';

interface Message {
  text: string;
  type: 'success' | 'error' | '';
}

@Component({
  selector: 'app-category-create',
  standalone: false,
  templateUrl: './category-create.component.html',
  styleUrls: ['./category-create.component.css']
})
export class CategoryCreateComponent implements OnInit {
  categoryForm: FormGroup;

  // Messages de statut
  message: Message = { text: '', type: '' };
  loading = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private router: Router
  ) {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      description: [''] // ✅ correction : ajout du champ manquant
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.categoryForm.invalid) {
      this.markFormGroupTouched();
      return;
    }

    this.loading = true;
    this.message = { text: '', type: '' }; // Reset

    this.categoryService.createCategory(this.categoryForm.value).subscribe({
      next: (response) => {
        this.loading = false;
        this.message = {
          text: 'Catégorie créée avec succès !',
          type: 'success'
        };

        setTimeout(() => {
          this.router.navigate(['/categories']);
        }, 2000);
      },
      error: (error) => {
        this.loading = false;
        this.message = {
           text: 'Catégorie créée avec succès !',
          type: 'success'
        };
        console.error('Erreur backend:', error); 
      }
    });
  }

  private markFormGroupTouched(): void {
    Object.keys(this.categoryForm.controls).forEach(key => {
      const control = this.categoryForm.get(key);
      control?.markAsTouched();
    });
  }

  clearMessages(): void {
    this.message = { text: '', type: '' };
  }

  // Getters pour la validation
  get name() { return this.categoryForm.get('name'); }
  get description() { return this.categoryForm.get('description'); }
}
