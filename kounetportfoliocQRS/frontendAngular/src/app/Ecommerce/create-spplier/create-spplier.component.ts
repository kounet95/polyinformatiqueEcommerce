import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SupplierService } from '../services/supplier.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-supplier',
  templateUrl: './create-spplier.component.html',
  styleUrls: ['./create-spplier.component.css'],
  standalone:false,
})
export class CreateSupplierComponent {
  supplierForm: FormGroup;
  isLoading = false;
  errorMsg = '';
  successMsg = '';

  constructor(
    private fb: FormBuilder,
    private supplierService: SupplierService,
    private router: Router
  ) {
    this.supplierForm = this.fb.group({
      fullname: ['', Validators.required],
      city: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      personToContact: ['', Validators.required]
    });
  }

  onSubmit() {
    this.errorMsg = '';
    this.successMsg = '';
    if (this.supplierForm.invalid) {
      this.errorMsg = 'Veuillez remplir correctement tous les champs.';
      return;
    }
    this.isLoading = true;
    this.supplierService.createSupplier(this.supplierForm.value).subscribe({
      next: () => {
        this.successMsg = 'Fournisseur créé avec succès !';
        this.isLoading = false;
        // Redirection vers la liste des fournisseurs après 2 secondes de flu
        setTimeout(() => {
          this.router.navigate(['/suppliers']);
        }, 2000);
      },
      error: (err) => {
        this.errorMsg = err.error?.message || 'Erreur lors de la création du fournisseur.';
        this.isLoading = false;
      }
    });
  }
}