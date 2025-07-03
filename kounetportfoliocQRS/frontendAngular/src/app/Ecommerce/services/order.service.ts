import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderDTO, OrderLineDTO, InvoiceDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

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

  /** Command: Payer une facture */
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

  /** Query: Liste des commandes */
  getAllOrders(): Observable<OrderDTO[]> {
    return this.http.get<OrderDTO[]>(`${this.queryBase}/orders`);
  }

  /** Query: Détail d'une commande */
  getOrderById(orderId: string): Observable<OrderDTO> {
    return this.http.get<OrderDTO>(`${this.queryBase}/orders/${orderId}`);
  }

  /** Event sourcing: Récupérer les événements */
  getOrderEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.commandBase}/events/${aggregateId}`);
  }
}
