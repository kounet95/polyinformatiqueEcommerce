import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { InvoiceDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class InvoiceService {
  private commandBase = `${ecpolyCommand.backend}/invoice/command`;
  private queryBase = `${ecpolyQuery.backend}/api`;
  private paymentBase = `${ecpolyCommand.backend}/api/payments`; // 🔄 Stripe endpoint

  constructor(private http: HttpClient) {}

  /** Command: Génération d'une facture */
  createInvoice(invoice: InvoiceDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/create`, invoice);
  }

  /** 🔵 Stripe: Créer une session de paiement (Checkout) */
  createCheckoutSession(invoice: InvoiceDTO): Observable<{ sessionId: string }> {
    return this.http.post<{ sessionId: string }>(
      `${this.paymentBase}/create-checkout-session`,
      invoice
    );
  }

  /** Query: Liste de toutes les factures */
  getAllInvoices(): Observable<InvoiceDTO[]> {
    return this.http.get<InvoiceDTO[]>(`${this.queryBase}/invoices`);
  }

  /** Query: Détail d'une facture par ID */
  getInvoiceById(id: string): Observable<InvoiceDTO> {
    return this.http.get<InvoiceDTO>(`${this.queryBase}/invoices/${id}`);
  }

  /** Command: Obtenir les événements d'une facture (Event Sourcing) */
  getInvoiceEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.commandBase}/events/${aggregateId}`);
  }
}
