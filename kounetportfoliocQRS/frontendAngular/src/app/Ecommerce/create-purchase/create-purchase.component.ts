import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PurchaseService } from '../services/purchase.service';
import { SupplierService } from '../services/supplier.service';
import { SupplierDTO } from '../../mesModels/models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-purchase',
  templateUrl: './create-purchase.component.html',
  styleUrls: ['./create-purchase.component.css'],
  standalone:false,
})
export class CreatePurchaseComponent implements OnInit {
  purchaseForm: FormGroup;
  suppliers: SupplierDTO[] = [];
  isLoading = false;
  errorMsg = '';
  successMsg = '';

  constructor(
    private fb: FormBuilder,
    private purchaseService: PurchaseService,
    private supplierService: SupplierService,
    private router: Router
  ) {
    this.purchaseForm = this.fb.group({
      supplierId: [null, Validators.required],
      createdAt: new Date().toISOString(),
      status: ['', Validators.required],
      total: [0, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    // recuperation de la liste des fournisseurs
    this.supplierService.getAllSuppliers(0, 100).subscribe({
      next: res => {
        // avec la pagination (PageResponse)
        this.suppliers = res.content ?? res; 
      },
      error: () => this.errorMsg = "Impossible de charger les fournisseurs."
    });
  }

  onSubmit() {
    this.errorMsg = '';
    this.successMsg = '';
    if (this.purchaseForm.invalid) {
      this.errorMsg = 'Veuillez remplir correctement tous les champs.';
      return;
    }
    this.isLoading = true;
    this.purchaseService.receivePurchase(this.purchaseForm.value).subscribe({
      next: () => {
        this.successMsg = 'Achat créé avec succès !';
        this.isLoading = false;
        setTimeout(() => {
          this.router.navigate(['/purchases']);
        }, 1500);
      },
      error: (err) => {
        this.errorMsg = err.error?.message || 'Erreur lors de la création de l\'achat.';
        this.isLoading = false;
      }
    });
  }
}