import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { loadStripe, Stripe } from '@stripe/stripe-js';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';
import { Observable } from 'rxjs'; 

@Injectable({
  providedIn: 'root'
})
export class StripeService {

   private commandBase = `${ecpolyCommand.backend}/api`;
  private queryBase = `${ecpolyQuery.backend}/api/stripe`;

  constructor(private http: HttpClient) {}

  private stripePromise = loadStripe('pk_test_51RjaG74EMj4mRh4Ig9G6XBkhmBu7e3fsqGmKkrZZ3WVQA3t9AvkP4zZuy4FQJBS6yfxzH7pi03K9N4beuis76nrn004vakKS5x');

  async redirectToCheckout(sessionId: string) {
    const stripe = await this.stripePromise;
    if (!stripe) {
      throw new Error('Stripe.js failed to load.');
    }
    return stripe.redirectToCheckout({ sessionId });
  }
  async createPaymentIntent(amount: number) {
    const stripe = await this.stripePromise;
    if (!stripe) {
      throw new Error('Stripe.js failed to load.');
    }
    const response = await fetch('/api/payments/create-payment-intent', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ amount })
    });
    return response.json();
  }

  /** Récupère la clé Stripe (ou début de la clé, ou "<null>") depuis le backend */
  getStripeKey(): Observable<string> {
    return this.http.get(`${this.commandBase}/api/test-stripe-key`, { responseType: 'text' });
  }
}
