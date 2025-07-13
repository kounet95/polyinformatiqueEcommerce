import { Injectable } from '@angular/core';
import { loadStripe, Stripe } from '@stripe/stripe-js';

@Injectable({
  providedIn: 'root'
})
export class StripeService {
  private stripePromise = loadStripe('pk_test_51RjaG74EMj4mRh4Ig9G6XBkhmBu7e3fsqGmKkrZZ3WVQA3t9AvkP4zZuy4FQJBS6yfxzH7pi03K9N4beuis76nrn004vakKS5x');

  async redirectToCheckout(sessionId: string) {
    const stripe = await this.stripePromise;
    if (!stripe) {
      throw new Error('Stripe.js failed to load.');
    }
    return stripe.redirectToCheckout({ sessionId });
  }
}
