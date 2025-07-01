import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SupplyService } from '../services/supply.service';
import { StockFormComponent } from '../stock-form/stock-form.component';

@Component({
  selector: 'app-creat-supply',
  templateUrl: './creat-supply.component.html',
  styleUrls: ['./creat-supply.component.css'], 

  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    StockFormComponent
  ]
})
export class CreatSupplyComponent implements OnInit {

  form: FormGroup;
  errorMsg = '';
  successMsg = '';

  constructor(
    private fb: FormBuilder,
    private supplyService: SupplyService
  ) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      stocks: this.fb.array([
        StockFormComponent.buildStockForm(this.fb)
      ])
    });
  }

  ngOnInit(): void {}

 get stocks(): FormArray<FormGroup> {
  return this.form.get('stocks') as FormArray<FormGroup>;
}


  addStock(): void {
    this.stocks.push(StockFormComponent.buildStockForm(this.fb));
  }

  removeStock(index: number): void {
    this.stocks.removeAt(index);
  }

  submit(): void {
    if (this.form.invalid) {
      this.errorMsg = 'Formulaire invalide.';
      return;
    }

    const payload = this.form.value;

    this.supplyService.createSupplyWithStock(payload).subscribe({
      next: () => {
        this.successMsg = 'Supply + Stocks créés avec succès !';
        this.form.reset();
        this.stocks.clear();
        this.addStock();
      },
      error: () => {
        this.errorMsg = 'Erreur lors de la création.';
      }
    });
  }

  
}
