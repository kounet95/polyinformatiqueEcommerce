import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { StockDTO, ProductSizeDTO, SupplierDTO } from '../../mesModels/models';
import { StockService } from '../services/stock.service';
import { ProductSizeService } from '../services/product-size.service';
import { SupplierService } from '../services/supplier.service';
import { AddressFormComponent } from '../address-form/address-form.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-creat-stock',
  templateUrl: './creat-stock.component.html',
  styleUrls: ['./creat-stock.component.css'],
   standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AddressFormComponent
  ]
})
export class CreatStockComponent implements OnInit {

  stockForm: FormGroup;
  productSizes: ProductSizeDTO[] = [];
  suppliers: SupplierDTO[] = [];

  loading = false;
  successMessage?: string;
  errorMessage?: string;

  constructor(
    private fb: FormBuilder,
    private stockService: StockService,
    private productSizeService: ProductSizeService,
    private supplierService: SupplierService
  ) {
    this.stockForm = this.fb.group({
      designation: ['', Validators.required],
      productSizeId: ['', Validators.required],
      supplierId: ['', Validators.required],
      purchasePrice: [0, [Validators.required, Validators.min(0)]],
      promoPrice: [0, [Validators.required, Validators.min(0)]],
      quantity: [0, [Validators.required, Validators.min(0)]],
      address: AddressFormComponent.buildAddressForm(this.fb)
    });
  }

  ngOnInit(): void {
    this.loadProductSizes();
    this.loadSuppliers();
  }

  loadProductSizes(): void {
    this.productSizeService.getAllProductSizes().subscribe({
      next: sizes => this.productSizes = sizes,
      error: () => this.errorMessage = 'Impossible de charger les tailles de produit.'
    });
  }

  loadSuppliers(): void {
    this.supplierService.getAllSuppliers().subscribe({
      next: suppliers => this.suppliers = suppliers.content,
      error: () => this.errorMessage = 'Impossible de charger les fournisseurs.'
    });
  }

  submit(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.stockForm.invalid) {
      this.errorMessage = 'Formulaire invalide.';
      return;
    }

    const raw = this.stockForm.value;

    const payload = {
      designation: raw.designation,
      productSizeId: raw.productSizeId,
      supplierId: raw.supplierId,
      purchasePrice: raw.purchasePrice,
      promoPrice: raw.promoPrice,
      quantity: raw.quantity,
      street: raw.address.street,
      city: raw.address.city,
      state: raw.address.state,
      zip: raw.address.zip,
      country: raw.address.country,
      appartment: raw.address.appartment,
      links: raw.address.links || []
    };

    this.stockService.createCustomerWithAddress(payload).subscribe({
      next: () => {
        this.successMessage = 'Stock et adresse créés avec succès !';
        this.stockForm.reset();
      },
      error: () => {
        this.errorMessage = ' Une erreur est survenue.';
      }
    });
  }

  get addressGroup(): FormGroup {
    return this.stockForm.get('address') as FormGroup;
  }

}
