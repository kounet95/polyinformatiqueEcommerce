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
  productSizeFrom: FormGroup;
  product: ProductDTO[] = [];
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
    this.productSizeFrom = this.fb.group({
      SizeProd: ['', [Validators.required]],
      product: ['', [Validators.required]],
      price: ['', [Validators.required, Validators.minLength(2)]],
      pricePromo: ['', [Validators.required, Validators.minLength(2)]],
      imageUrl: ['', [Validators.required, Validators.minLength(2)]]
    });
  }

  ngOnInit(): void {
    this.loadProduct();
  }

  loadProduct(): void {
    this.productService.getAllProducts().subscribe({
      next: data => {
        this.product = data.content || [];
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
    if (this.productSizeFrom.invalid) {
      this.productSizeFrom.markAllAsTouched();
      return;
    }
    const raw = this.productSizeFrom.value;
    const productSize: ProductSizeDTO = {
      id: '',
      sizeProd: raw.SizeProd,
      prodId: raw.product,
      price: raw.price,
      imageUrl: '',
      pricePromo: raw.pricePromo 
    };
    this.loading = true;
    this.productSizeService.createProductSize(productSize, this.selectedFile ?? undefined).subscribe({
      next: () => {
        this.successMessage = 'ProduitSize créé avec succès !';
        this.loading = false;
        this.productSizeFrom.reset({
          SizeProd: '',
          product: '',
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