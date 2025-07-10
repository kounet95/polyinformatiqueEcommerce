import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CustomerService } from '../services/customer.service';
import { AuthService } from '../../services/AuthService';
import { CommonModule } from '@angular/common';
import { AddressFormComponent } from '../address-form/address-form.component';

@Component({
  selector: 'app-create-customer',
  templateUrl: './creat-customer.component.html',
  styleUrls: ['./creat-customer.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    AddressFormComponent
  ]
})
export class CreatCustomerComponent implements OnInit {
  form: FormGroup;
  customerId?: string;
  errorMsg = '';
  successMsg = '';
  userdata: any;

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private authService: AuthService
  ) {
    this.form = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      address: AddressFormComponent.buildAddressForm(this.fb)
    });
  }

  ngOnInit(): void {
    this.loadUserData();
  }

  loadUserData() {
    const userId = this.authService.getUserId ? this.authService.getUserId() : null;
    const userProfile = this.authService.getUserProfile ? this.authService.getUserProfile() : null;

    if (!userId) {
      this.errorMsg = 'Utilisateur non authentifié.';
      return;
    }

    this.customerService.getCustomerById(userId).subscribe({
      next: (customer) => {
        if (customer) {
          this.userdata = customer;
          this.customerId = customer.id;

          this.form.patchValue({
            firstname: customer.firstName || '',
            lastname: customer.lastName || '',
            email: customer.email || '',
            phone: customer.phone || ''
          });

        } else {
          this.customerId = userId;

          this.form.patchValue({
            firstname: userProfile?.firstname || '',
            lastname: userProfile?.lastname || '',
            email: userProfile?.email || '',
            phone: userProfile?.phone || ''
          });

        }
      },
      error: () => {
        this.customerId = userId;

        this.form.patchValue({
          firstname: userProfile?.firstname || '',
          lastname: userProfile?.lastname || '',
          email: userProfile?.email || '',
          phone: userProfile?.phone || ''
        });
      }
    });
  }

submit() {
  if (this.form.valid) {
    const address = this.form.value.address;

    const payload = {
      firstname: this.form.value.firstname,
      lastname: this.form.value.lastname,
      email: this.form.value.email,
      phone: this.form.value.phone,

      //Ici on "déplie" l'objet adresse
      street: address.street,
      city: address.city,
      state: address.state,
      zip: address.zip,
      country: address.country,
      appartment: address.appartment,

      // Optionnel : tu peux passer links si tu veux gérer ça côté front
      links: [
        {
          targetType: 'CUSTOMER',
          targetId: this.customerId ?? '', // ou autre ID
          addressId: '' // laissé vide, c'est ton aggregate qui lie tout
        }
      ]
    };

    console.log('Payload envoyé :', payload);

    this.customerService.createCustomerWithAddress(payload).subscribe({
      next: () => {
        this.successMsg = 'Client et adresse créés et liés avec succès !';
      },
      error: () => {
        this.errorMsg = 'Une erreur est survenue lors de la sauvegarde.';
      }
    });
  }
}



  get addressGroup(): FormGroup {
    return this.form.get('address') as FormGroup;
  }

}
