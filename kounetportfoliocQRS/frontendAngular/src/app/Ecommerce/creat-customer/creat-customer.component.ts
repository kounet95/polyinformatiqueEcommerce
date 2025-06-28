import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AddressService } from '../services/address.service';
import { CustomerService } from '../services/customer.service';
import { AddressDTO } from '../../mesModels/models';
import { AuthService } from '../../services/AuthService';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-customer',
  templateUrl: './creat-customer.component.html',
  styleUrls: ['./creat-customer.component.css'],
  imports: [
    CommonModule,
    ReactiveFormsModule 
  ]
})
export class CreatCustomerComponent implements OnInit {
  customerForm: FormGroup;
  addresses: AddressDTO[] = [];
  isLoading = false;
  errorMsg = '';
  successMsg = '';
  userdata: any;
  customerId: string | undefined;

  constructor(
    private fb: FormBuilder,
    private addressService: AddressService,
    private customerService: CustomerService,
    private authService: AuthService
  ) {
    this.customerForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      addressId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadUserData();
  }

  loadUserData() {
    const userId = this.authService.getUserId ? this.authService.getUserId() : null;
    const userProfile = this.authService.getUserProfile ? this.authService.getUserProfile() : null;

    if (!userId) {
      this.errorMsg = "Utilisateur non authentifié.";
      return;
    }

    this.customerService.getCustomerById(userId).subscribe({
      next: (customer) => {
        if (customer) {
          this.userdata = customer;
          this.customerId = customer.id;
          this.customerForm.patchValue({
            firstname: customer.firstname || '',
            lastname: customer.lastname || '',
            email: customer.email || '',
            phone: customer.phone || '',
            addressId: customer.addressId || ''
          });
          // Charger uniquement les adresses liées à ce client
          if (this.customerId) {
  this.loadCustomerAddresses(this.customerId);
}
        } else {
          this.customerId = userId;
          this.customerForm.patchValue({
            firstname: userProfile ? userProfile.firstname : '',
            lastname: userProfile ? userProfile.lastname : '',
            email: userProfile ? userProfile.email : '',
            phone: userProfile ? userProfile.phone : ''
          });
          // Charger les adresses même si le customer n'existe pas encore
          this.loadCustomerAddresses(userId);
        }
      },
      error: () => {
        this.customerId = userId;
        this.customerForm.patchValue({
          firstname: userProfile ? userProfile.firstname : '',
          lastname: userProfile ? userProfile.lastname : '',
          email: userProfile ? userProfile.email : '',
          phone: userProfile ? userProfile.phone : ''
        });
        // Charger les adresses même si le customer n'existe pas encore
        this.loadCustomerAddresses(userId);
      }
    });
  }

  // Charge uniquement les adresses liées à ce client
  loadCustomerAddresses(customerId: string) {
    this.addressService.getAddressesByCustomerId(customerId).subscribe({
      next: (addresses) => {
        this.addresses = Array.isArray(addresses) ? addresses : [];
      },
      error: () => {
        this.addresses = [];
        this.errorMsg = "Impossible de charger les adresses du client.";
      }
    });
  }

  onSubmit() {
    this.errorMsg = '';
    this.successMsg = '';
    this.customerForm.markAllAsTouched();
    if (this.customerForm.invalid) {
      this.errorMsg = 'Veuillez remplir tous les champs obligatoires.';
      return;
    }
    this.isLoading = true;

    const userId = this.authService.getUserId ? this.authService.getUserId() : null;
    if (!userId) {
      this.errorMsg = "Utilisateur non identifié, impossible de créer le client.";
      this.isLoading = false;
      return;
    }

    this.customerService.getCustomerById(userId).subscribe({
      next: (customer) => {
        this.userdata = customer;
        this.customerId = customer.id;
        this.updateCustomerWithAddress(this.customerId!);
      },
      error: () => {
        // Si pas trouvé, création du customer
        const customer = {
          id: userId,
          firstname: this.customerForm.value.firstname,
          lastname: this.customerForm.value.lastname,
          email: this.customerForm.value.email,
          phone: this.customerForm.value.phone,
          addressId: this.customerForm.value.addressId
        };
        this.customerService.createCustomer(customer).subscribe({
          next: () => {
            this.customerId = userId;
            this.successMsg = "Client créé avec succès !";
            this.isLoading = false;
            this.customerForm.reset();
          },
          error: () => {
            this.errorMsg = "Erreur lors de la création du client.";
            this.isLoading = false;
          }
        });
      }
    });
  }

  updateCustomerWithAddress(customerId: string) {
    if (!customerId) {
      this.errorMsg = "Impossible de récupérer l'id du client.";
      this.isLoading = false;
      return;
    }
    const updatedCustomer = {
      ...this.userdata,
      firstname: this.customerForm.value.firstname,
      lastname: this.customerForm.value.lastname,
      email: this.customerForm.value.email,
      phone: this.customerForm.value.phone,
      addressId: this.customerForm.value.addressId
    };
    this.customerService.updateCustomer(updatedCustomer).subscribe({
      next: () => {
        this.successMsg = 'Adresse du client mise à jour avec succès !';
        this.isLoading = false;
        this.customerForm.reset();
      },
      error: () => {
        this.errorMsg = "Erreur lors de la mise à jour du client.";
        this.isLoading = false;
      }
    });
  } 
}