import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ProductService } from '../services/produit.service';
import { ProductDTO, ProductSize } from '../../mesModels/models';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css'],
})
export class CreateProductComponent {
  selectedFile: File | null = null;
  sizeOptions = Object.values(ProductSize);
  selectedSize: ProductSize = ProductSize.MEDIUM;

  constructor(private productService: ProductService) {}

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    } else {
      this.selectedFile = null;
    }
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      const raw = form.value;
      const product: ProductDTO = {
        id: '', // sera généré côté backend
        name: raw.name,
        description: raw.description,
        price: Number(raw.price),
        createdAt: new Date().toISOString(),
        subcategoryId: raw.subcategoryId,
        socialGroupId: raw.socialGroupId,
        imageUrl: '', // géré côté backend
        isActive: !!raw.isActive,
        couleurs: raw.couleurs,
        closedAt: raw.closedAt,
        productSize: this.selectedSize
      };
      this.productService.createProduct(product, this.selectedFile ?? undefined).subscribe({
        next: () => {
          alert('Produit créé avec succès !');
          form.reset();
          this.selectedFile = null;
        },
        error: () => {
          alert('Erreur lors de la création du produit.');
        }
      });
    }
  }
}