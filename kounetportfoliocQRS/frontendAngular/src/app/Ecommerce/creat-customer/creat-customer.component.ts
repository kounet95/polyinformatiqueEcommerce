import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AddressService } from '../services/address.service';
import { CustomerService } from '../services/customer.service';
import { AddressDTO } from '../../mesModels/models';

@Component({
  selector: 'app-create-customer',
  templateUrl: './creat-customer.component.html',
  styleUrls: ['./creat-customer.component.css'],
  standalone: false,
})
export class CreatCustomerComponent implements OnInit {
  customerForm: FormGroup;
  addresses: AddressDTO[] = [];
  isNewAddress = false; 
  isLoading = false;
  errorMsg = '';
  successMsg = '';

  constructor(
    private fb: FormBuilder,
    private addressService: AddressService,
    private customerService: CustomerService
  ) {
    this.customerForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      addressId: [''],

      // champs pour la nouvelle adresse
      street: [''],
      city: [''],
      state: [''],
      zip: [''],
      country: [''],
      appartment: ['']
    });
  }

  ngOnInit(): void {
    this.addressService.getAllAddresses().subscribe({
      next: data => this.addresses = Array.isArray(data) ? data : (data || []),
      error: () => this.errorMsg = "Impossible de charger les adresses."
    });
  }

  onSubmit() {
    this.errorMsg = '';
    this.successMsg = '';
    if (this.customerForm.invalid) {
      this.errorMsg = 'Veuillez remplir tous les champs obligatoires.';
      return;
    }
    this.isLoading = true;
    if (this.isNewAddress) {
      
      const address: AddressDTO = {
        street: this.customerForm.value.street,
        city: this.customerForm.value.city,
        state: this.customerForm.value.state,
        zip: this.customerForm.value.zip,
        country: this.customerForm.value.country,
        appartment: this.customerForm.value.appartment
      };
      this.addressService.createAddress(address).subscribe({
        next: (created: AddressDTO) => {
          const customer = {
            firstname: this.customerForm.value.firstname,
            lastname: this.customerForm.value.lastname,
            email: this.customerForm.value.email,
            phone: this.customerForm.value.phone,
            addressId: String(created.id),
            createdAt: new Date().toISOString()
          };
          this.customerService.createCustomer(customer).subscribe({
            next: () => {
              this.successMsg = 'Client créé avec succès !';
              this.isLoading = false;
              this.customerForm.reset();
            },
            error: () => {
              this.errorMsg = "Erreur lors de la création du client.";
              this.isLoading = false;
            }
          });
        },
        error: () => {
          this.errorMsg = "Erreur lors de la création de l'adresse.";
          this.isLoading = false;
        }
      });
    } else {
      // une des options pour un utilisation d'une adresse existante
      const customer = {
        firstname: this.customerForm.value.firstname,
        lastname: this.customerForm.value.lastname,
        email: this.customerForm.value.email,
        phone: this.customerForm.value.phone,
        addressId: String(this.customerForm.value.addressId) //conversion explicite!
      };
      this.customerService.createCustomer(customer).subscribe({
        next: () => {
          this.successMsg = 'Client créé avec succès !';
          this.isLoading = false;
          this.customerForm.reset();
        },
        error: () => {
          this.errorMsg = "Erreur lors de la création du client.";
          this.isLoading = false;
        }
      });
    }
  }
}