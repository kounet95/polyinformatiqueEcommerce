import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-address-form', 
  templateUrl: './address-form.component.html',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
})
export class AddressFormComponent {
 @Input() parentForm!: FormGroup;

  static buildAddressForm(fb: FormBuilder) {
    return fb.group({
      street: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      zip: ['', Validators.required],
      country: ['', Validators.required],
      appartment: ['']
    });
  }
}

