import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Country, CountryService } from '../services/country.service';

@Component({
  selector: 'app-address-form',
  templateUrl: './address-form.component.html',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
})
export class AddressFormComponent implements OnInit {
  @Input() selectedCountry = '';
  @Output() selectedCountryChange = new EventEmitter<string>();
  countries: Country[] = [];
  loading = true;
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

  constructor(private countryService: CountryService) {

  }
 ngOnInit(): void {
    this.countryService.getCountries().subscribe(countries => {
      this.countries = countries;
      this.loading = false;
    });
  }

  onSelect(value: string) {
    this.selectedCountryChange.emit(value);
  }
}