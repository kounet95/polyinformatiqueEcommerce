import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryDTO, SubcategoryDTO, SocialGroupDTO, ProductDTO, ProductSizeDTO } from '../../mesModels/models';
import { CategoryService } from '../services/category.service';
import { SouscategoriesService } from '../services/souscategories.service';
import { CategoriesocialesService } from '../services/categoriesociales.service';
import { ProductService } from '../services/produit.service';
import { ProductSizeService } from '../services/product-size.service';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css'],
  standalone: false,
})
export class CreateProductComponent implements OnInit {
  productForm!: FormGroup;
  addressGroup!: FormGroup;

  categories: CategoryDTO[] = [];
  sousCategories: SubcategoryDTO[] = [];
  categoriesSociales: SocialGroupDTO[] = [];
  selectedFile: File | null = null;
  productSizes: ProductSizeDTO[] = [];

  loading = false;
  successMessage?: string;
  errorMessage?: string;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private souscategoriesService: SouscategoriesService,
    private categoriesocialesService: CategoriesocialesService,
    private productService: ProductService,
    private productSizeService: ProductSizeService
  ) {}

  ngOnInit(): void {
    this.addressGroup = this.fb.group({
      street: ['', Validators.required],
      city: ['', Validators.required],
      zipCode: ['', Validators.required],
      country: ['', Validators.required]
    });

    this.productForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      description: ['', [Validators.required]],
      categoryId: ['', Validators.required],
      subcategoryId: ['', Validators.required],
      socialGroupId: ['', Validators.required],
      productSizeId: ['', Validators.required],
      address: this.addressGroup,
      isActive: [true]
    });

    this.loadCategories();

    this.categoriesocialesService.getAllSocialGroups().subscribe({
      next: data => this.categoriesSociales = data,
      error: () => this.errorMessage = 'Impossible de charger les groupes sociaux.',
    });

    this.productForm.get('categoryId')?.valueChanges.subscribe(categoryId => {
      if (!categoryId) {
        this.sousCategories = [];
        this.productForm.patchValue({ subcategoryId: '' });
        return;
      }
      this.souscategoriesService.getAllSubcategories().subscribe({
        next: subs => {
          this.sousCategories = subs.filter(sc => sc.categoryId === categoryId);
          this.productForm.patchValue({ subcategoryId: '' });
        },
        error: () => this.errorMessage = 'Impossible de charger les sous-catégories.',
      });
    });

    this.productSizeService.getAllProductSizes().subscribe({
      next: page => this.productSizes = page.content || [],
      error: () => { this.productSizes = []; }
    });
  }

  loadCategories(): void {
    this.categoryService.getAllCategories().subscribe({
      next: cats => this.categories = cats,
      error: () => this.errorMessage = 'Impossible de charger les catégories.',
    });
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    this.selectedFile = input.files?.[0] ?? null;
  }

  onSubmit() {
    this.successMessage = undefined;
    this.errorMessage = undefined;

    if (this.productForm.invalid) {
      this.productForm.markAllAsTouched();
      return;
    }

    const raw = this.productForm.value;
    const product: ProductDTO = {
      id: '',
      name: raw.name,
      description: raw.description,
      createdAt: new Date().toISOString(),
      subcategoryId: raw.subcategoryId,
      socialGroupId: raw.socialGroupId,
      models: 'k',
      isActive: !!raw.isActive
    };

    this.loading = true;
    this.productService.createProduct(product, this.selectedFile ?? undefined).subscribe({
      next: () => {
        this.successMessage = 'Produit créé avec succès !';
        this.loading = false;
        this.productForm.reset({
          name: '',
          description: '',
          categoryId: '',
          subcategoryId: '',
          socialGroupId: '',
          productSizeId: '',
          address: {
            street: '',
            city: '',
            zipCode: '',
            country: ''
          },
          isActive: true
        });
        this.selectedFile = null;
      },
      error: () => {
        this.errorMessage = 'Erreur lors de la création du produit.';
        this.loading = false;
      }
    });
  }
}