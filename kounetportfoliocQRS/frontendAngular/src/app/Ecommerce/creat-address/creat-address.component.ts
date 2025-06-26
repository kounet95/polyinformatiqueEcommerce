import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AddressService } from '../services/address.service';
import { Router } from '@angular/router';
import { AddressDTO, CustomerEcommerceDTO, ShippingDTO, StockDTO, SupplierDTO } from '../../mesModels/models';
import { zip } from 'rxjs';
import { ProductSizeService } from '../services/product-size.service';
import { ShippingService } from '../services/shipping.service';
import { SupplierService } from '../services/supplier.service';
import { StockService } from '../services/stock.service';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-create-address',
  templateUrl: './creat-address.component.html',
  styleUrls: ['./creat-address.component.css'],
  standalone: false,
})
export class CreatAddressComponent implements OnInit {
  addressForm: FormGroup;
  isLoading = false;
  errorMsg = '';
  successMsg = '';
  customer : CustomerEcommerceDTO[]=[];
  store : StockDTO[]=[];
  shipping : ShippingDTO []=[];
  supplier :  SupplierDTO[]=[];

  constructor(
    private fb: FormBuilder,
    private addressService: AddressService,
    private router: Router,
    private productSizeService: ProductSizeService,
    private supplierService: SupplierService,
    private customerService: CustomerService,
    private shippingService: ShippingService,
    private storeService: StockService
  ) {
    this.addressForm = this.fb.group({
      street: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      zip: ['', Validators.required],
      appartement: ['', Validators.required],
      postalCode: ['', Validators.required],
      country: ['', Validators.required],
      customer: [''],
      store: [''],
      supplier: [''],
      shipping: [''],
    });
  }

  ngOnInit(): void {
    this.loadCustomer();
    this.loadShipping();
    this.loadSupplier();
    this.loadStore();
  }

  onSubmit() {
    this.errorMsg = '';
    this.successMsg = '';
    if (this.addressForm.invalid) {
      this.errorMsg = 'Veuillez remplir correctement tous les champs.';
      return;
    }
    this.isLoading = true;
    this.addressService.createAddress(this.addressForm.value as AddressDTO).subscribe({
      next: () => {
        this.successMsg = 'Adresse créée avec succès !';
        this.isLoading = false;
        setTimeout(() => {
          this.router.navigate(['/addresses']);
        }, 2000);
      },
      error: (err) => {
        this.errorMsg = err.error?.message || 'Erreur lors de la création de l\'adresse.';
        this.isLoading = false;
      }
    });
  }

  loadCustomer(): void {
    this.customerService.getAllCustomers().subscribe({
      next: data => {
        this.customer = Array.isArray(data) ? data : (data || []);
      },
      error: () => this.errorMsg = "Impossible de charger les clients."
    });
  }

  loadShipping(): void {
    this.shippingService.getAllShippings().subscribe({
      next: data => {
        this.shipping = Array.isArray(data) ? data : (data || []);
      },
      error: () => this.errorMsg = "Impossible de charger les livraisons."
    });
  }

  loadSupplier(): void {
    this.supplierService.getAllSuppliers().subscribe({
      next: data => {
        this.supplier = Array.isArray(data) ? data : (data.content || []);
      },
      error: () => this.errorMsg = "Impossible de charger les fournisseurs."
    });
  }

  loadStore(): void {
    this.storeService.getAllStocks().subscribe({
      next: data => {
        this.store = Array.isArray(data) ? data : (data || []);
      },
      error: () => this.errorMsg = "Impossible de charger les stocks."
    });
  }


    
}