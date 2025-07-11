import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderStatusDTO } from '../../mesModels/models';
import { ecpolyQuery } from '../../../mesApi/ecpolyQuery';

@Injectable({
  providedIn: 'root'
})
export class OrderStatusService {
  constructor(private http: HttpClient) {}

  /** EventStream: Suivre le statut de la commande par ID (SSE) */
  watchOrderStatus(orderId: string): Observable<OrderStatusDTO> {
    return this.http.get<OrderStatusDTO>(
      `${ecpolyQuery.backend}/api/orderstatus/watch/${orderId}`,
      { responseType: 'text/event-stream' as 'json' }
    );
  }

  /** EventStream: Suivre le statut de la commande par code-barres (SSE) */
  watchOrderStatusByBarcode(barcode: string): Observable<OrderStatusDTO> {
    return this.http.get<OrderStatusDTO>(
      `${ecpolyQuery.backend}/api/orderstatus/watch-by-barcode/${barcode}`,
      { responseType: 'text/event-stream' as 'json' }
    );
  }
}