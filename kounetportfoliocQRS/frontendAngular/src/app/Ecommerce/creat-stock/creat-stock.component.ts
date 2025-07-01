import { Component, Input, OnInit } from '@angular/core';
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

  @Input() parentForm!: FormGroup;
  productSizes: ProductSizeDTO[] = [];
  suppliers: SupplierDTO[] = [];
  error: string | null = null;

  isLoading = false;
  successMsg = '';
  errorMsg = '';

  page: number = 0;
  size: number = 10;
  totalElements: number = 0;
  loading = false;
  successMessage?: string;
  errorMessage?: string;

  constructor(
    private fb: FormBuilder,
    private stockService: StockService,
    private productSizeService: ProductSizeService,
    private supplierService: SupplierService
  ) {
    this.parentForm = this.fb.group({
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

  loadSuppliers(): void {
    this.loading = true;
    this.supplierService.getAllSuppliers(this.page, this.size).subscribe({
      next:  (result: any) => {
        if (Array.isArray(result)) {
          this.suppliers =  result;
          this.totalElements = result.length;
        } else {
            this.suppliers = result.content ?? [];
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
    this.successMessage = '';
    this.errorMessage = '';

    if (this.parentForm.invalid) {
      this.errorMessage = 'Formulaire invalide.';
      return;
    }

    const raw = this.parentForm.value;

    const payload = {
      
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
        this.parentForm.reset();
      },
      error: () => {
        this.errorMessage = ' Une erreur est survenue.';
      }
    });
  }

  get addressGroup(): FormGroup {
    return this.parentForm.get('address') as FormGroup;
  }

}
