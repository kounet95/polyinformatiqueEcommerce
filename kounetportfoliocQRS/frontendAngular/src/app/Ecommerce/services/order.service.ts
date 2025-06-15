import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderDTO, OrderLineDTO, InvoiceDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {}

  /**
   * Création d'une commande
   */
  createOrder(order: OrderDTO): Observable<string> {
    return this.http.post<string>(
      `${ecpolyCommand.backend}/order/command/create`,
      order
    );
  }

  /**
   * Ajout d'un produit à une commande existante
   */
  addProductToOrder(orderId: string, orderLine: OrderLineDTO): Observable<string> {
    return this.http.post<string>(
      `${ecpolyCommand.backend}/order/command/${orderId}/add-product`,
      orderLine
    );
  }

  /**
   * Confirmation d'une commande
   */
  confirmOrder(orderId: string): Observable<string> {
    return this.http.put<string>(
      `${ecpolyCommand.backend}/order/command/${orderId}/confirm`,
      {}
    );
  }

  /**
   * Génération d'une facture pour une commande
   */
  generateInvoice(orderId: string, invoice: InvoiceDTO): Observable<string> {
    return this.http.post<string>(
      `${ecpolyCommand.backend}/order/command/${orderId}/generate-invoice`,
      invoice
    );
  }

  /**
   * Paiement d'une facture
   */
  payInvoice(invoiceId: string): Observable<string> {
    return this.http.put<string>(
      `${ecpolyCommand.backend}/order/command/invoice/${invoiceId}/pay`,
      {}
    );
  }

  /**
   * Lancement de la livraison d'une commande
   */
  startShipping(orderId: string): Observable<string> {
    return this.http.put<string>(
      `${ecpolyCommand.backend}/order/command/${orderId}/start-shipping`,
      {}
    );
  }

  /**
   * Marquer une commande comme livrée
   */
  deliverOrder(orderId: string): Observable<string> {
    return this.http.put<string>(
      `${ecpolyCommand.backend}/order/command/${orderId}/deliver`,
      {}
    );
  }

  /**
   * Récupérer toutes les commandes (Query)
   */
  getAllOrders(): Observable<OrderDTO[]> {
    return this.http.get<OrderDTO[]>(
      `${ecpolyQuery.backend}/api/orders`
    );
  }

  /**
   * Récupérer une commande par son ID (Query)
   */
  getOrderById(orderId: string): Observable<OrderDTO> {
    return this.http.get<OrderDTO>(
      `${ecpolyQuery.backend}/api/orders/${orderId}`
    );
  }

  /**
   * Récupérer les évènements d'une commande (Event sourcing)
   */
  getOrderEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(
      `${ecpolyCommand.backend}/order/command/events/${aggregateId}`
    );
  }
}