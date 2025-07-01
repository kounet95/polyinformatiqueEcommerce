import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SupplierService } from '../services/supplier.service';
import { ProductSizeService } from '../services/product-size.service';
import { ProductSizeDTO } from '../../mesModels/models';
import { CommonModule } from '@angular/common';
import { AddressFormComponent } from '../address-form/address-form.component';

@Component({
  selector: 'app-create-supplier',
  templateUrl: './create-supplier.component.html',
  styleUrls: ['./create-supplier.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AddressFormComponent
  ]
})
export class CreateSupplierComponent implements OnInit {

  supplierForm: FormGroup;
  productSizes: ProductSizeDTO[] = [];
  loading: boolean = true;
  error: string | null = null;

  isLoading = false;
  successMsg = '';
  errorMsg = '';

  page: number = 0;
  size: number = 10;
  totalElements: number = 0;

  constructor(
    private fb: FormBuilder,
    private supplierService: SupplierService,
    private productSizeService: ProductSizeService
  ) {
    this.supplierForm = this.fb.group({
      fullname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      personToContact: ['', Validators.required],
      productSizeId: ['', Validators.required],
      address: AddressFormComponent.buildAddressForm(this.fb)
    });
  }

  ngOnInit(): void {
    this.loadProductSizes();
  }

  loadProductSizes(): void {
    this.loading = true;
    this.productSizeService.getAllProductsise(this.page, this.size).subscribe({
      next: (result: any) => {
        if (Array.isArray(result)) {
          this.productSizes = result;
          this.totalElements = result.length;
        } else {
          this.productSizes = result.content ?? [];
          this.totalElements = result.totalElements ?? 0;
        }
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement des tailles de produit.';
        this.loading = false;
      }
    });
  }

  submit(): void {
    this.successMsg = '';
    this.errorMsg = '';

    if (this.supplierForm.invalid) {
      this.errorMsg = 'Veuillez remplir correctement tous les champs.';
      return;
    }

    this.isLoading = true;

    const raw = this.supplierForm.value;

    const payload = {
      fullname: raw.fullname,
      email: raw.email,
      personToContact: raw.personToContact,
      productSizeId: raw.productSizeId,
      street: raw.address.street,
      city: raw.address.city,
      state: raw.address.state,
      zip: raw.address.zip,
      country: raw.address.country,
      appartment: raw.address.appartment,
      links: raw.address.links || []
    };

    this.supplierService.createSupplierWithAddress(payload).subscribe({
      next: () => {
        this.successMsg = 'Fournisseur + adresse créés avec succès !';
        this.isLoading = false;
        this.supplierForm.reset();
      },
      error: () => {
        this.errorMsg = 'Une erreur est survenue.';
        this.isLoading = false;
      }
    });
  }

  get addressGroup(): FormGroup {
    return this.supplierForm.get('address') as FormGroup;
  }
}
