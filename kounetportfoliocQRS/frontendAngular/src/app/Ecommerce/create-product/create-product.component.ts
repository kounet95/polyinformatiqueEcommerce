import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ProductService } from '../services/produit.service';
import { ProductDTO } from '../../mesModels/models';

@Component({
  selector: 'app-create-product',
  standalone: false,
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent {
  selectedFile: File | null = null;

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
      // Construction d'un ProductDTO (on retire media et imageUrl local si besoin)
      const { media, ...product } = form.value as ProductDTO & { media?: any };
      this.productService.createProduct(product, this.selectedFile || undefined).subscribe({
        next: (result) => {
          alert('Produit créé avec succès !');
          form.reset();
          this.selectedFile = null;
        },
        error: (err) => {
          alert('Erreur lors de la création du produit.');
        }
      });
    }
  }
}