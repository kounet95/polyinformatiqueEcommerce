import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderDTO, OrderLineDTO, InvoiceDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

// 👉 Nouveau type pour le résumé côté Front
export interface OrderSummary {
  orderId: string;
  customerId: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private commandBase = `${ecpolyCommand.backend}/order/command`;
  private queryBase = `${ecpolyQuery.backend}/api`;

  constructor(private http: HttpClient) {}

  /** Command: Création d'une commande */
  createOrder(order: OrderDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/create`, order);
  }

  /** Command: Ajout d'un produit */
  addProductToOrder(orderId: string, orderLine: OrderLineDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/${orderId}/add-product`, orderLine);
  }

  /** Command: Confirmer la commande */
  confirmOrder(orderId: string): Observable<string> {
    return this.http.put<string>(`${this.commandBase}/${orderId}/confirm`, {});
  }

  /** Command: Générer une facture */
  generateInvoice(orderId: string, invoice: InvoiceDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/${orderId}/generate-invoice`, invoice);
  }

  /**  Command: Payer une facture */
  payInvoice(invoiceId: string): Observable<string> {
    return this.http.put<string>(`${this.commandBase}/invoice/${invoiceId}/pay`, {});
  }

  /** Command: Lancer la livraison */
  startShipping(orderId: string): Observable<string> {
    return this.http.put<string>(`${this.commandBase}/${orderId}/start-shipping`, {});
  }

  /** Command: Marquer comme livrée */
  deliverOrder(orderId: string): Observable<string> {
    return this.http.put<string>(`${this.commandBase}/${orderId}/deliver`, {});
  }

  /** Command: Annuler une commande */
  cancelOrder(orderId: string, reason = 'Cancelled by user'): Observable<string> {
    return this.http.delete<string>(`${this.commandBase}/${orderId}?reason=${encodeURIComponent(reason)}`);
  }

  /** Command: Générer le PaymentIntent Stripe */
  createPaymentIntent(order: OrderDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/payment-intent`, order);
  }

  /** Query: Liste des résumés de commandes */
  getOrderSummaries(): Observable<OrderSummary[]> {
    return this.http.get<OrderSummary[]>(`${this.queryBase}/orders`);
  }

  /**  Query: Résumé d'une commande par ID */
  getOrderSummaryById(orderId: string): Observable<OrderSummary> {
    return this.http.get<OrderSummary>(`${this.queryBase}/orders/${orderId}`);
  }

  /** Events sourcing: Historique des événements */
  getOrderEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.commandBase}/events/${aggregateId}`);
  }

}
