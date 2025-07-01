import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductSizeDTO, SupplierDTO } from '../../mesModels/models';
import { AddressFormComponent } from '../address-form/address-form.component';
import { ProductSizeService } from '../services/product-size.service';
import { SupplierService } from '../services/supplier.service';

@Component({
  selector: 'app-stock-form',
  standalone: true,
  templateUrl: './stock-form.component.html',
  styleUrls: ['./stock-form.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AddressFormComponent
  ]
})
export class StockFormComponent implements OnInit {
  @Input() parentForm!: FormGroup;
  @Input() productSizes: ProductSizeDTO[] = [];
  @Input() suppliers: SupplierDTO[] = [];

  error: string | null = null;
  page: number = 0;
  size: number = 10;
  totalElements: number = 0;
  loading = false;

  constructor(
    private productSizeService: ProductSizeService,
    private supplierService: SupplierService
  ) {}

  static buildStockForm(fb: FormBuilder) {
    return fb.group({
      productSizeId: ['', Validators.required],
      supplierId: ['', Validators.required],
      purchasePrice: [0, [Validators.required, Validators.min(0)]],
      promoPrice: [0, [Validators.required, Validators.min(0)]],
      quantity: [0, [Validators.required, Validators.min(0)]],
      address: AddressFormComponent.buildAddressForm(fb)
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
      next: (result: any) => {
        if (Array.isArray(result)) {
          this.suppliers = result;
          this.totalElements = result.length;
        } else {
          this.suppliers = result.content ?? [];
          this.totalElements = result.totalElements ?? 0;
        }
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement des fournisseurs.';
        this.loading = false;
      }
    });
  }

  get addressGroup(): FormGroup {
    return this.parentForm.get('address') as FormGroup;
  }
}
