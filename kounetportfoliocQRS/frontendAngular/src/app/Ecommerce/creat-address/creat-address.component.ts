import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AddressService } from '../services/address.service';
import { Router } from '@angular/router';
import { AuthService } from '../../services/AuthService';
import { AddressDTO, ShippingDTO, StockDTO, SupplierDTO } from '../../mesModels/models';
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
  customerIds: string[] = [];
  storeIds: string[] = [];
  shippingIds: string[] = [];
  supplierIds: string[] = [];
  linkType: string = ''; // 'customer', 'stock', 'supplier', 'shipping'

  constructor(
    private fb: FormBuilder,
    private addressService: AddressService,
    private router: Router,
    private authService: AuthService,
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
      addressType: [''],
      store: [''],
      supplier: [''],
      shipping: [''],
    });
  }

  ngOnInit(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.customerService.getCustomerById(userId).subscribe({
        next: data => this.customerIds = data ? [data.id] : [],
        error: () => this.errorMsg = "Impossible de charger le client."
      });
      this.shippingService.getShippingsByUserId(userId).subscribe({
        next: data => this.shippingIds = Array.isArray(data) ? data.map((s: ShippingDTO) => s.id) : [],
        error: () => this.errorMsg = "Impossible de charger les livraisons."
      });
      this.supplierService.getSuppliersByUserId(userId).subscribe({
        next: data => this.supplierIds = Array.isArray(data) ? data.map((s: SupplierDTO) => s.id) : [],
        error: () => this.errorMsg = "Impossible de charger les fournisseurs."
      });
      this.storeService.getStocksByUserId(userId).subscribe({
        next: data => this.storeIds = Array.isArray(data) ? data.map((s: StockDTO) => s.id) : [],
        error: () => this.errorMsg = "Impossible de charger les stocks."
      });
    }
  }

  onLinkTypeChange(type: string) {
    this.linkType = type;
    // Reset les champs non utilisés
    this.addressForm.patchValue({
      customer: '',
      addressType: '',
      store: '',
      supplier: '',
      shipping: ''
    });
  }

  onSubmit() {
    this.errorMsg = '';
    this.successMsg = '';
    if (this.addressForm.invalid) {
      this.errorMsg = 'Veuillez remplir correctement tous les champs.';
      return;
    }
    this.isLoading = true;

    const formValue = this.addressForm.value;
    let addressPayload: any = {
      street: formValue.street,
      city: formValue.city,
      state: formValue.state,
      zip: formValue.zip,
      appartement: formValue.appartement,
      postalCode: formValue.postalCode,
      country: formValue.country,
    };

    // Ajoute le lien selon le type sélectionné
    if (this.linkType === 'customer') {
      addressPayload.customer = formValue.customer;
      addressPayload.addressType = formValue.addressType;
    } else if (this.linkType === 'stock') {
      addressPayload.store = formValue.store;
    } else if (this.linkType === 'supplier') {
      addressPayload.supplier = formValue.supplier;
    } else if (this.linkType === 'shipping') {
      addressPayload.shipping = formValue.shipping;
    }

    this.addressService.createAddress(addressPayload as AddressDTO).subscribe({
      next: () => {
        this.successMsg = 'Adresse créée avec succès !';
        this.isLoading = false;
        this.addressForm.reset();
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
}