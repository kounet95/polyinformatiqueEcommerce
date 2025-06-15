import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PurchaseDTO, PurchaseItemDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class PurchaseService {
  private commandBase = `${ecpolyCommand.backend}/purchase/command`;
  private queryBase = `${ecpolyQuery.backend}/api`;

  constructor(private http: HttpClient) {}

  // Command: faire un achat
  receivePurchase(purchase: PurchaseDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/receive`, purchase);
  }

  // Query: Récupération tous les achats
  getAllPurchases(): Observable<PurchaseDTO[]> {
    return this.http.get<PurchaseDTO[]>(`${this.queryBase}/purchases`);
  }

  // Query: Récupération un achat par ID
  getPurchaseById(id: string): Observable<PurchaseDTO> {
    return this.http.get<PurchaseDTO>(`${this.queryBase}/purchases/${id}`);
  }

  // Query: Récupération les items d'un achat
  getAllPurchaseItems(): Observable<PurchaseItemDTO[]> {
    return this.http.get<PurchaseItemDTO[]>(`${this.queryBase}/purchaseitems`);
  }

  // Query: Récupération un item d'un achat par ID
  getPurchaseItemById(id: string): Observable<PurchaseItemDTO> {
    return this.http.get<PurchaseItemDTO>(`${this.queryBase}/purchaseitems/${id}`);
  }

  // Command: Obtenir l'historique des événements d'un achat (event sourcing)
  getPurchaseEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.commandBase}/events/${aggregateId}`);
  }
}