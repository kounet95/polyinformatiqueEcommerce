import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SupplierService } from '../services/supplier.service';
import { Router } from '@angular/router';
import { AddressService } from '../services/address.service'; // Pour charger les adresses
import { AddressDTO } from '../../mesModels/models';

@Component({
  selector: 'app-create-supplier',
  templateUrl: './create-spplier.component.html',
  styleUrls: ['./create-spplier.component.css'],
  standalone: false,
})
export class CreateSupplierComponent implements OnInit {
  supplierForm: FormGroup;
  isLoading = false;
  errorMsg = '';
  successMsg = '';

  addresses: AddressDTO[] = [];

  constructor(
    private fb: FormBuilder,
    private supplierService: SupplierService,
    private addressService: AddressService,
    private router: Router
  ) {
    this.supplierForm = this.fb.group({
      fullname: ['', Validators.required],
      addressId: [''],
      email: ['', [Validators.required, Validators.email]],
      personToContact: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // Charge les adresses pour le select
    this.addressService.getAllAddresses().subscribe({
      next: data => {
        this.addresses = Array.isArray(data) ? data : (data || []);
      }
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