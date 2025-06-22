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

  /** Command: Ajout d'un produit à une commande existante */
  addProductToOrder(orderId: string, orderLine: OrderLineDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/${orderId}/add-product`, orderLine);
  }

  /** Command: Confirmation d'une commande */
  confirmOrder(orderId: string): Observable<string> {
    return this.http.put<string>(`${this.commandBase}/${orderId}/confirm`, {});
  }

  /** Command: Génération d'une facture pour une commande */
  generateInvoice(orderId: string, invoice: InvoiceDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/${orderId}/generate-invoice`, invoice);
  }

  /** Command: Paiement d'une facture */
  payInvoice(invoiceId: string): Observable<string> {
    return this.http.put<string>(`${this.commandBase}/invoice/${invoiceId}/pay`, {});
  }

  /** Command: Lancement de la livraison */
  startShipping(orderId: string): Observable<string> {
    return this.http.put<string>(`${this.commandBase}/${orderId}/start-shipping`, {});
  }

  /** Command: Marquer comme livrée */
  deliverOrder(orderId: string): Observable<string> {
    return this.http.put<string>(`${this.commandBase}/${orderId}/deliver`, {});
  }

  /** Query: Toutes les commandes */
  getAllOrders(): Observable<OrderDTO[]> {
    return this.http.get<OrderDTO[]>(`${this.queryBase}/orders`);
  }

  /** Query: Détail d'une commande */
  getOrderById(orderId: string): Observable<OrderDTO> {
    return this.http.get<OrderDTO>(`${this.queryBase}/orders/${orderId}`);
  }

  /** Command: Obtenir les événements d'une commande (Event Sourcing) */
  getOrderEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.commandBase}/events/${aggregateId}`);
  }
}