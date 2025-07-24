import { Component, OnInit } from '@angular/core';
import { CategoryDTO, SubcategoryDTO } from '../../mesModels/models';
import { CategoryService } from '../services/category.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SouscategoriesService } from '../services/souscategories.service';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
  categoryForm: FormGroup;
  successMessage?: string;
  errorMessage?: string;
  loading = false;
  currentEditingCategory?: CategoryDTO;

  categories: CategoryDTO[] = [];
  subCategories : SubcategoryDTO[] = [];

  constructor(private categoryService: CategoryService, private subCategoryService: SouscategoriesService, private route: Router,  private fb: FormBuilder ){
      
       this.categoryForm = this.fb.group({
        name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]]
       });

  }

  ngOnInit(): void {
    this.loadCategories();
 
  
  }

 loadCategories() {
  this.loading = true;
  this.categoryService.getAllCategories().subscribe({
    next: (cats: CategoryDTO[]) => {
      this.subCategoryService.getAllSubcategories().subscribe({
        next: (sousCats: SubcategoryDTO[]) => {
          this.categories = cats.map(cat => ({
            ...cat,
            children: sousCats
              .filter((sc: SubcategoryDTO) => sc.categoryId === cat.id)
              .map((sc: SubcategoryDTO) => ({ id: sc.id, name: sc.name }))
          }));
          this.loading = false;
        },
        error: () => {
          this.errorMessage = "Erreur lors du chargement des sous-catégories.";
          this.loading = false;
        }
      });
    },
    error: () => {
      this.errorMessage = "Erreur lors du chargement des catégories.";
      this.loading = false;
    }
  });
}

  update(): void {
  this.successMessage = undefined;
  this.errorMessage = undefined;

  if (this.categoryForm.invalid || !this.currentEditingCategory) {
    this.categoryForm.markAllAsTouched();
    return;
  }

  const category: CategoryDTO = {
    id: this.currentEditingCategory.id, 
    name: this.categoryForm.value.name,
    sousCategories: [] 
  };

  this.loading = true;
  this.categoryService.updateCatgorie(category).subscribe({
    next: () => {
      this.successMessage = 'Catégorie modifiée avec succès !';
      this.loading = false; 
      this.categoryForm.reset();
      this.currentEditingCategory = undefined;
      this.loadCategories(); 
    },
    error: () => {
      this.errorMessage = "Erreur lors de la modification de la catégorie.";
      this.loading = false;
    }
  });
}


 
  editCategory(cat: CategoryDTO) {
    this.currentEditingCategory = cat;
    // il faut Remplir le formulaire avec les données de la catégorie selectionnee
    this.categoryForm.patchValue({
      name: cat.name
    });
  }
  cancelEdit() {
    this.currentEditingCategory = undefined;
    this.categoryForm.reset();
  }

  showCategory(cat: CategoryDTO) {
  this.subCategoryService.getSousCategoriesByCategoryId(cat.id).subscribe({
    next: sousCats => this.subCategories = sousCats || [],
    error: err => console.error('Erreur lors du chargement des sous-catégories', err)
  });
}


}