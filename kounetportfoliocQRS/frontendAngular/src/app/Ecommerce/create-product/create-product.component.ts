import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryDTO, SubcategoryDTO, SocialGroupDTO, ProductDTO, ProductSize } from '../../mesModels/models';
import { CategoryService } from '../services/category.service';
import { SouscategoriesService } from '../services/souscategories.service';
import { CategoriesocialesService } from '../services/categoriesociales.service';
import { ProductService } from '../services/produit.service';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css'],
  standalone: false,
})
export class CreateProductComponent implements OnInit {
  productForm: FormGroup;
  categories: CategoryDTO[] = [];
  sousCategories: SubcategoryDTO[] = [];
  categoriesSociales: SocialGroupDTO[] = [];
  selectedFile: File | null = null;
  sizeOptions = Object.values(ProductSize);
  loading = false;
  successMessage?: string;
  errorMessage?: string;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private souscategoriesService: SouscategoriesService,
    private categoriesocialesService: CategoriesocialesService,
    private productService: ProductService
  ) {
    this.productForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      description: ['', [Validators.required]],
      price: [null, [Validators.required, Validators.min(0)]],
      categoryId: [null, Validators.required],
      subcategoryId: [null, Validators.required],
      socialGroupId: [null, Validators.required],
      productSize: [ProductSize.MEDIUM, Validators.required],
      isActive: [true],
      couleurs: ['']
    });
  }

  ngOnInit(): void {
    this.loadCategories();

    // Charger tous les groupes sociaux au lancement
    this.categoriesocialesService.getAllSocialGroups().subscribe({
      next: data => this.categoriesSociales = data,
      error: () => this.errorMessage = "Impossible de charger les groupes sociaux."
    });

    // Quand la catégorie change, charger les sous-catégories correspondantes
    this.productForm.get('categoryId')?.valueChanges.subscribe(categoryId => {
      if (!categoryId) {
        this.sousCategories = [];
        this.productForm.patchValue({ subcategoryId: null });
        return;
      }
      this.souscategoriesService.getAllSubcategories().subscribe({
        next: subs => {
          this.sousCategories = subs.filter(sc => sc.categoryId === categoryId);
          this.productForm.patchValue({ subcategoryId: null });
        },
        error: () => this.errorMessage = "Impossible de charger les sous-catégories."
      });
    });

    // Si tu veux filtrer les groupes sociaux selon la sous-catégorie, décommente ce bloc et adapte selon ta logique
    /*
    this.productForm.get('subcategoryId')?.valueChanges.subscribe(subcategoryId => {
      if (!subcategoryId) {
        this.productForm.patchValue({ socialGroupId: null });
        return;
      }
      this.categoriesocialesService.getAllSocialGroups().subscribe({
        next: data => {
          // Exemple: filtre les groupes sociaux liés à cette sous-catégorie
          this.categoriesSociales = data.filter(sg => sg.subcategoryIds?.includes(subcategoryId));
          this.productForm.patchValue({ socialGroupId: null });
        },
        error: () => this.errorMessage = "Impossible de charger les groupes sociaux."
      });
    });
    */
  }

  loadCategories(): void {
    this.categoryService.getAllCategories().subscribe({
      next: cats => this.categories = cats,
      error: () => this.errorMessage = "Impossible de charger les catégories."
    });
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    } else {
      this.selectedFile = null;
    }
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
      price: Number(raw.price),
      createdAt: new Date().toISOString(),
      subcategoryId: raw.subcategoryId,
      socialGroupId: raw.socialGroupId,
      imageUrl: '',
      isActive: !!raw.isActive,
      couleurs: raw.couleurs,
      productSize: raw.productSize
    };
    this.loading = true;
    this.productService.createProduct(product, this.selectedFile ?? undefined).subscribe({
      next: () => {
        this.successMessage = 'Produit créé avec succès !';
        this.loading = false;
        this.productForm.reset({ productSize: ProductSize.MEDIUM, isActive: true });
        this.selectedFile = null;
      },
      error: () => {
        this.errorMessage = 'Erreur lors de la création du produit.';
        this.loading = false;
      }
    });
  }
}