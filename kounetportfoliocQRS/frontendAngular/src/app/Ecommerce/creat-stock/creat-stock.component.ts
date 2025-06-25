import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StockDTO, ProductSizeDTO, SupplierDTO, AddressDTO } from '../../mesModels/models';
import { StockService } from '../services/stock.service';
import { ProductSizeService } from '../services/product-size.service';
import { SupplierService } from '../services/supplier.service';
import { AddressService } from '../services/address.service';

@Component({
  selector: 'app-creat-stock',
  standalone: false,
  templateUrl: './creat-stock.component.html',
  styleUrls: ['./creat-stock.component.css']
})
export class CreatStockComponent implements OnInit {
  stockForm: FormGroup;
  productSizes: ProductSizeDTO[] = [];
  suppliers: SupplierDTO[] = [];
  addresses: AddressDTO[] = [];

  loading = false;
  successMessage?: string;
  errorMessage?: string;

  constructor(
    private stockService: StockService,
    private fb: FormBuilder,
    private productSizeService: ProductSizeService,
    private supplierService: SupplierService,
    private addressService: AddressService
  ) {
    this.stockForm = this.fb.group({
      designation: ['', [Validators.required]],
      productSizeId: ['', [Validators.required]],
      supplierId: ['', [Validators.required]],
      purchasePrice: ['', [Validators.required]],
      promoPrice: [''],
      quantity: ['', [Validators.required]],
      addressId: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadProductSizes();
    this.loadSuppliers();
    this.loadAddresses();
  }

  loadProductSizes(): void {
    this.productSizeService.getAllProductSizes().subscribe({
      next: data => {
        this.productSizes = Array.isArray(data) ? data : (data.content || []);
      },
      error: () => this.errorMessage = "Impossible de charger les tailles de produits."
    });
  }

  loadSuppliers(): void {
    this.supplierService.getAllSuppliers().subscribe({
      next: data => {
        this.suppliers = Array.isArray(data) ? data : (data.content || []);
      },
      error: () => this.errorMessage = "Impossible de charger les fournisseurs."
    });
  }

  loadAddresses(): void {
    this.addressService.getAllAddresses().subscribe({
      next: data => {
        this.addresses = Array.isArray(data) ? data : (data.content || []);
      },
      error: () => this.errorMessage = "Impossible de charger les adresses."
    });
  }

  onSubmit() {
    this.successMessage = undefined;
    this.errorMessage = undefined;
    if (this.stockForm.invalid) {
      this.stockForm.markAllAsTouched();
      return;
    }
    const raw = this.stockForm.value;
    const stock: StockDTO = {
      id: '',
      designation: raw.designation,
      productSizeId: raw.productSizeId,
      supplierId: raw.supplierId,
      purchasePrice: raw.purchasePrice,
      promoPrice: raw.promoPrice,
      quantity: raw.quantity,
      addressId: raw.addressId
    };
    this.loading = true;
    this.stockService.createStock(stock).subscribe({
      next: () => {
        this.successMessage = 'Stock créé avec succès !';
        this.loading = false;
        this.stockForm.reset({
          designation: '',
          productSizeId: '',
          supplierId: '',
          purchasePrice: '',
          promoPrice: '',
          quantity: '',
          addressId: ''
        });
      },
      error: () => {
        this.errorMessage = 'Erreur lors de la création du stock.';
        this.loading = false;
      }
    });
  }
}