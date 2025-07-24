import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductDTO, ProductSizeDTO, SizeProd } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';
import { ProductService } from '../services/produit.service';

@Component({
  selector: 'app-create-product-size',
  standalone: false,
  templateUrl: './create-product-size.component.html',
  styleUrls: ['./create-product-size.component.css']
})
export class CreateProductSizeComponent implements OnInit {
  productSizeForm: FormGroup;
  products: ProductDTO[] = [];
  sizeEnumValues = Object.values(SizeProd);
  loading = false;
  successMessage?: string;
  errorMessage?: string;

  /**les fichiers images par côté */
  imageFiles: { [key: string]: File | null } = {
    front: null,
    back: null,
    left: null,
    right: null
  };

  constructor(
    private productSizeService: ProductSizeService,
    private fb: FormBuilder,
    private productService: ProductService
  ) {
    this.productSizeForm = this.fb.group({
      sizeProd: ['', [Validators.required]],
      prodId: ['', [Validators.required]],
      price: ['', [Validators.required]],
      pricePromo: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: data => {
        this.products = Array.isArray(data) ? data : (data.content || []);
      },
      error: () => this.errorMessage = "Impossible de charger les produits."
    });
  }

  /** Gestion pour les images */
  onFileSelected(event: Event, side: 'front' | 'back' | 'left' | 'right') {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.imageFiles[side] = input.files[0];
    } else {
      this.imageFiles[side] = null;
    }
  }

  
  onSubmit() {
    this.successMessage = undefined;
    this.errorMessage = undefined;

    if (this.productSizeForm.invalid) {
      this.productSizeForm.markAllAsTouched();
      return;
    }

    const raw = this.productSizeForm.value;

    const productSize: ProductSizeDTO = {
      id: '',
      sizeProd: raw.sizeProd,
      prodId: raw.prodId,
      price: raw.price,
      pricePromo: raw.pricePromo,
      frontUrl: '',
      backUrl: '',
      leftUrl: '',
      rightUrl: ''
    };

    this.loading = true;

    this.productSizeService.createProductSize(productSize, this.imageFiles).subscribe({
      next: () => {
        this.successMessage = 'Taille de produit créée avec succès !';
        this.loading = false;
        this.productSizeForm.reset({
          sizeProd: '',
          prodId: '',
          price: '',
          pricePromo: ''
        });
        this.imageFiles = { front: null, back: null, left: null, right: null };
      },
      error: () => {
        this.errorMessage = 'Erreur lors de la création de la taille du produit.';
        this.loading = false;
      }
    });
  }
}
