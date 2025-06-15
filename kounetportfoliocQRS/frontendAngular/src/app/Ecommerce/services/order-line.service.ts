import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderLineDTO } from '../../mesModels/models';
import { ecpolyCommand } from '../../../mesApi/ecpolyCommand';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class OrderLineService {

  constructor(private http: HttpClient) {}

  /**
   * Créer une ligne de commande (OrderLine)
   */
  createOrderLine(orderLine: OrderLineDTO): Observable<string> {
    return this.http.post<string>(
      `${ecpolyCommand.backend}/order-line/command/create`,
      orderLine
    );
  }

  /**
   * Récupérer toutes les lignes de commande (Query)
   */
  getAllOrderLines(): Observable<OrderLineDTO[]> {
    return this.http.get<OrderLineDTO[]>(
      `${ecpolyQuery.backend}/api/orderlines`
    );
  }

  /**
   * Récupérer une ligne de commande par son ID (Query)
   */
  getOrderLineById(orderLineId: string): Observable<OrderLineDTO> {
    return this.http.get<OrderLineDTO>(
      `${ecpolyQuery.backend}/api/orderlines/${orderLineId}`
    );
  }

  /**
   * Récupérer les événements d'une ligne de commande (Event sourcing)
   */
  getOrderLineEvents(aggregateId: string): Observable<any[]> {
    return this.http.get<any[]>(
      `${ecpolyCommand.backend}/order-line/command/events/${aggregateId}`
    );
  }
}