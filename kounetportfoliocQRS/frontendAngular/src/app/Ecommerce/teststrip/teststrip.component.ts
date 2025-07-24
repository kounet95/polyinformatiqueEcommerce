import { Component, OnInit } from '@angular/core';
import { StripeService } from '../services/stripe.service';
@Component({
  selector: 'app-stripe-test',
  template: `
    <div>
      <button (click)="checkStripeKey()">Test Backend Stripe Key</button>
      <div *ngIf="result">Résultat: {{ result }}</div>
    </div>
  `
})
export class TeststripComponent implements OnInit {

  result: string = '';

  constructor(private stripeDebug: StripeService) {}

  ngOnInit(): void {}

  checkStripeKey() {
    this.stripeDebug.getStripeKey().subscribe({
      next: (val) => this.result = val,
      error: (err) => this.result = 'Erreur: ' + err.message
    });
  }

}
