import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductDTO, ProductSizeDTO, SizeProd } from '../../mesModels/models';
import { ProductSizeService } from '../services/product-size.service';
import { ProductService } from '../services/produit.service';

@Component({
  selector: 'app-create-product-size',
  standalone: false,
  templateUrl: './create-product-size.component.html',
  styleUrl: './create-product-size.component.css'
})
export class CreateProductSizeComponent implements OnInit {
  productSizeForm: FormGroup;
  products: ProductDTO[] = [];
  sizeEnumValues = Object.values(SizeProd);
  loading = false;
  successMessage?: string;
  errorMessage?: string;
  selectedFile: File | null = null;

  constructor(
    private productSizeService: ProductSizeService, 
    private fb: FormBuilder, 
    private productService: ProductService
  ) {
    this.productSizeForm = this.fb.group({
      sizeProd: ['', [Validators.required]],
      prodId: ['', [Validators.required]], // prodId = id du produit
      price: ['', [Validators.required]],
      pricePromo: ['', [Validators.required]],
      imageUrl: ['']
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
    if (this.productSizeForm.invalid) {
      this.productSizeForm.markAllAsTouched();
      return;
    }
    const raw = this.productSizeForm.value;
    const productSize: ProductSizeDTO = {
      id: '',
      size: raw.sizeProd,
      prodId: raw.prodId, // juste l'id du produit
      price: raw.price,
      imageUrl: 'k', 
      pricePromo: raw.pricePromo 
    };
    this.loading = true;
    this.productSizeService.createProductSize(productSize, this.selectedFile ?? undefined).subscribe({
      next: () => {
        this.successMessage = 'ProduitSize créé avec succès !';
        this.loading = false;
        this.productSizeForm.reset({
          sizeProd: '',
          prodId: '',
          price: '',
          pricePromo: '',
          imageUrl: ''
        });
        this.selectedFile = null;
      },
      error: () => {
        this.errorMessage = 'Erreur lors de la création du produitSize.';
        this.loading = false;
      }
    });
  }
}