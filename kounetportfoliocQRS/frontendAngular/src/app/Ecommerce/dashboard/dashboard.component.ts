import { Component, OnInit } from '@angular/core';
import { CategoryDTO } from '../../mesModels/models';
import { CategoryService } from '../services/category.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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
  
  categories: CategoryDTO[] = [];

  constructor(private categoryService: CategoryService, private route: Router,  private fb: FormBuilder ){
      
       this.categoryForm = this.fb.group({
        name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]]
       });

  }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(){
     this.categoryService.getAllCategories().subscribe({
      next: cats => this.categories = cats || [],
      error: () => console.error('Erreur chargement catégories')
    })
  }

  update():void {
     this.successMessage = undefined;
    this.errorMessage = undefined;

    if (this.categoryForm.invalid) {
      this.categoryForm.markAllAsTouched();
      return;
    }

    const category: CategoryDTO = {
      id: '', 
      name: this.categoryForm.value.name,
      sousCategories: [] 
    };

    this.loading = true;
    this.categoryService.updateCatgorie(category).subscribe({
      next: () => {
        this.successMessage = 'Catégorie modifiée avec succès !';
        this.loading = false; 
        this.categoryForm.reset();
      },
      error: err => {
        this.errorMessage = "Erreur lors de la modification de la catégorie.";
        this.loading = false;
      }
    });
  }

 
  

  

}
