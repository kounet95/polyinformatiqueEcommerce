import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ShippingDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class ShippingService {
  private commandBase = `${ecpolyCommand.backend}/shipping/command`;
  private queryBase = `${ecpolyQuery.backend}/api`;

  constructor(private http: HttpClient) {}

  // Command: Création d'une expédition
  createShipping(shipping: ShippingDTO): Observable<string> {
    return this.http.post<string>(`${this.commandBase}/create`, shipping);
  }

  // Query: Récupération toutes les expéditions
  getAllShippings(): Observable<ShippingDTO[]> {
    return this.http.get<ShippingDTO[]>(`${this.queryBase}/shippings`);
  }

  // Query: Récupération une expédition par ID
  getShippingById(id: string): Observable<ShippingDTO> {
    return this.http.get<ShippingDTO>(`${this.queryBase}/shippings/${id}`);
  }

  // Command: Récupération les événements d'une expédition (event sourcing)
  getShippingEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.commandBase}/events/${aggregateId}`);
  }
}