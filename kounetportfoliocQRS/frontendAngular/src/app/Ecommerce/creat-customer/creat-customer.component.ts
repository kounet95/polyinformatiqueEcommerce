import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AddressService } from '../services/address.service';
import { CustomerService } from '../services/customer.service';
import { AddressDTO } from '../../mesModels/models';
import { AuthService } from '../../services/AuthService';

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
  userdata: any;

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
      addressId: [''],

      // Champs pour la nouvelle adresse
      street: [''],
      city: [''],
      state: [''],
      zip: [''],
      country: [''],
      appartment: ['']
    });
  }

  ngOnInit(): void {
    this.loadUserData();
    this.addressService.getAllAddresses().subscribe({
      next: data => this.addresses = Array.isArray(data) ? data : (data || []),
      error: () => this.errorMsg = "Impossible de charger les adresses."
    });
  }

  // Charge ou crée le customer à partir de l'utilisateur authentifié
  loadUserData() {
    const userId = this.authService.getUserId();
    if (userId) {
      this.customerService.getCustomerById(userId).subscribe({
        next: (customer) => {
          if (customer) {
            this.userdata = customer;
            this.customerForm.patchValue({
              firstname: customer.firstname || '',
              lastname: customer.lastname || '',
              email: customer.email || '',
              phone: customer.phone || '',
              addressId: customer.addressId || ''
            });
          }
        },
        error: () => {
          // S'il n'existe pas, on le crée à partir des infos d'auth
          const authUser = this.authService.getUserInfo ? this.authService.getUserInfo() : {};
          const customerToCreate = {
            id: userId,
            firstname: authUser.firstname || '',
            lastname: authUser.lastname || '',
            email: authUser.email || '',
            phone: authUser.phone || '',
            createdAt: new Date().toISOString(),
            addressId: ''
          };
          this.customerService.createCustomer(customerToCreate).subscribe({
            next: (created) => {
              this.userdata = created;
              this.customerForm.patchValue({
                firstname: created.firstname,
                lastname: created.lastname,
                email: created.email,
                phone: created.phone,
                addressId: created.addressId || '',
              });
            },
            error: () => {
              this.errorMsg = "Impossible de créer l'utilisateur.";
            }
          });
        }
      });
    }
  }

  // gestion sur lappelée à chaque changement du radio
  onAddressTypeChange(newValue: boolean) {
    this.isNewAddress = newValue;
    if (newValue) {
      // Réinitialisation des champs adresse
      this.customerForm.patchValue({
        street: '',
        city: '',
        state: '',
        zip: '',
        country: '',
        appartment: '',
        addressId: ''
      });
    } else {
      // Si adresse existante, vider les champs adresse
      this.customerForm.patchValue({
        street: '',
        city: '',
        state: '',
        zip: '',
        country: '',
        appartment: ''
      });
    }
  }

  onSubmit() {
    this.errorMsg = '';
    this.successMsg = '';
    if (this.customerForm.invalid) {
      this.errorMsg = 'Veuillez remplir tous les champs obligatoires.';
      return;
    }
    this.isLoading = true;

    const customerId = this.userdata && this.userdata.id ? this.userdata.id : null;
    if (!customerId) {
      this.errorMsg = "Utilisateur non identifié, impossible de créer le client.";
      this.isLoading = false;
      return;
    }

    if (this.isNewAddress) {
      // Création d'une nouvelle adresse
      const address: AddressDTO = {
        street: this.customerForm.value.street,
        city: this.customerForm.value.city,
        state: this.customerForm.value.state,
        zip: this.customerForm.value.zip,
        country: this.customerForm.value.country,
        appartment: this.customerForm.value.appartment,
        customer: customerId
      };
      this.addressService.createAddress(address).subscribe({
        next: (created: AddressDTO) => {
          const customer = {
            id: customerId,
            firstname: this.customerForm.value.firstname,
            lastname: this.customerForm.value.lastname,
            email: this.customerForm.value.email,
            phone: this.customerForm.value.phone,
            addressId: String(created.id)
          };
          this.customerService.updateCustomer(customer).subscribe({
            next: () => {
              this.successMsg = 'Client mis à jour avec succès !';
              this.isLoading = false;
              this.customerForm.reset();
            },
            error: () => {
              this.errorMsg = "Erreur lors de la mise à jour du client.";
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
      // Utilisation d'une adresse existante
      const customer = {
        id: customerId,
        firstname: this.customerForm.value.firstname,
        lastname: this.customerForm.value.lastname,
        email: this.customerForm.value.email,
        phone: this.customerForm.value.phone,
        addressId: String(this.customerForm.value.addressId)
      };
      this.customerService.updateCustomer(customer).subscribe({
        next: () => {
          this.successMsg = 'Client mis à jour avec succès !';
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
}